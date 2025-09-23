package com.lucasbueno.luziachallenge.presentation.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucasbueno.luziachallenge.domain.audio.AudioRecorder
import com.lucasbueno.luziachallenge.domain.model.ChatMessage
import com.lucasbueno.luziachallenge.domain.model.MessageAuthor
import com.lucasbueno.luziachallenge.domain.usecase.ClearChatUseCase
import com.lucasbueno.luziachallenge.domain.usecase.ObserveChatUseCase
import com.lucasbueno.luziachallenge.domain.usecase.RequestCompletionUseCase
import com.lucasbueno.luziachallenge.domain.usecase.SaveMessageUseCase
import com.lucasbueno.luziachallenge.domain.usecase.SpeakTextUseCase
import com.lucasbueno.luziachallenge.domain.usecase.TranscribeAudioUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

class ChatViewModel(
    private val observeChatUseCase: ObserveChatUseCase,
    private val saveMessageUseCase: SaveMessageUseCase,
    private val transcribeAudioUseCase: TranscribeAudioUseCase,
    private val requestCompletionUseCase: RequestCompletionUseCase,
    private val clearChatUseCase: ClearChatUseCase,
    private val speakTextUseCase: SpeakTextUseCase,
    private val audioRecorder: AudioRecorder
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    private var latestMessages: List<ChatMessage> = emptyList()
    private var transcriptionJob: Job? = null

    init {
        observeMessages()
    }

    fun onRecordToggle() {
        if (audioRecorder.isRecording) {
            stopRecordingAndTranscribe()
        } else {
            startRecording()
        }
    }

    fun onToggleTts(enabled: Boolean) {
        _uiState.update { it.copy(isTtsEnabled = enabled) }
        if (!enabled) {
            speakTextUseCase.stop()
        }
    }

    fun onClearConversation() {
        viewModelScope.launch {
            runCatching { clearChatUseCase() }
            _uiState.update { it.copy(snackbarMessage = "Conversation cleared") }
        }
    }

    fun onSnackbarShown() {
        _uiState.update { it.copy(snackbarMessage = null) }
    }

    private fun startRecording() {
        viewModelScope.launch {
            val result = runCatching { audioRecorder.startRecording() }
            result.onSuccess {
                _uiState.update { state ->
                    state.copy(
                        isRecording = true,
                        snackbarMessage = null
                    )
                }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(snackbarMessage = error.localizedMessage ?: "Unable to start recording")
                }
            }
        }
    }

    private fun stopRecordingAndTranscribe() {
        if (transcriptionJob?.isActive == true) return
        transcriptionJob = viewModelScope.launch {
            _uiState.update { it.copy(isRecording = false, isLoadingTranscription = true) }
            val audioFile = runCatching { audioRecorder.stopRecording() }.getOrNull()
            if (audioFile == null) {
                _uiState.update {
                    it.copy(
                        isLoadingTranscription = false,
                        snackbarMessage = "No audio captured"
                    )
                }
                return@launch
            }
            handleTranscription(audioFile)
        }.also { job ->
            job.invokeOnCompletion { transcriptionJob = null }
        }
    }

    private suspend fun handleTranscription(audioFile: File) {
        val transcription = transcribeAudioUseCase(audioFile)
        audioFile.delete()
        transcription.onFailure { throwable ->
            if (throwable is CancellationException) throw throwable
            _uiState.update {
                it.copy(
                    isLoadingTranscription = false,
                    snackbarMessage = throwable.localizedMessage ?: "Unable to transcribe audio"
                )
            }
        }.onSuccess { text ->
            val normalized = text.trim()
            if (normalized.isEmpty()) {
                _uiState.update {
                    it.copy(
                        isLoadingTranscription = false,
                        snackbarMessage = "Transcription was empty"
                    )
                }
                return
            }
            val userMessage = ChatMessage(
                author = MessageAuthor.USER,
                content = normalized
            )
            runCatching { saveMessageUseCase(userMessage) }
            _uiState.update { it.copy(isLoadingTranscription = false, isLoadingResponse = true) }
            requestAssistantResponse(history = latestMessages + userMessage)
        }
    }

    private fun requestAssistantResponse(history: List<ChatMessage>) {
        viewModelScope.launch {
            val result = requestCompletionUseCase(history)
            result.onFailure { error ->
                if (error is CancellationException) throw error
                _uiState.update {
                    it.copy(
                        isLoadingResponse = false,
                        snackbarMessage = error.localizedMessage ?: "Unable to fetch AI response"
                    )
                }
            }.onSuccess { assistantMessage ->
                runCatching { saveMessageUseCase(assistantMessage) }
                _uiState.update { it.copy(isLoadingResponse = false) }
                if (uiState.value.isTtsEnabled) {
                    speakAssistant(assistantMessage.content)
                }
            }
        }
    }

    private fun speakAssistant(message: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isSynthesizing = true) }
            runCatching { speakTextUseCase(message) }
            _uiState.update { it.copy(isSynthesizing = false) }
        }
    }

    private fun observeMessages() {
        viewModelScope.launch {
            observeChatUseCase().collectLatest { messages ->
                latestMessages = messages
                _uiState.update {
                    it.copy(
                        messages = messages.map { message ->
                            ChatMessageUiModel(
                                id = message.id,
                                author = message.author,
                                content = message.content,
                                timestamp = message.timestamp,
                                isUser = message.author == MessageAuthor.USER
                            )
                        }
                    )
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        speakTextUseCase.release()
    }
}
