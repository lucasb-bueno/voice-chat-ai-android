package com.lucasbueno.luziachallenge.presentation.chat

import com.lucasbueno.luziachallenge.domain.model.MessageAuthor

data class ChatUiState(
    val messages: List<ChatMessageUiModel> = emptyList(),
    val isRecording: Boolean = false,
    val isLoadingTranscription: Boolean = false,
    val isLoadingResponse: Boolean = false,
    val isSynthesizing: Boolean = false,
    val isTtsEnabled: Boolean = false,
    val snackbarMessage: String? = null
)

data class ChatMessageUiModel(
    val id: Long?,
    val author: MessageAuthor,
    val content: String,
    val timestamp: Long,
    val isUser: Boolean
)
