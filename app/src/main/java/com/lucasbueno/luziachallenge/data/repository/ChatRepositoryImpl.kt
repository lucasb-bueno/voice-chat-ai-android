package com.lucasbueno.luziachallenge.data.repository

import com.lucasbueno.luziachallenge.data.local.dao.ChatMessageDao
import com.lucasbueno.luziachallenge.data.local.mapper.toDomain
import com.lucasbueno.luziachallenge.data.local.mapper.toEntity
import com.lucasbueno.luziachallenge.data.remote.api.OpenAiService
import com.lucasbueno.luziachallenge.data.remote.dto.ChatCompletionMessageDto
import com.lucasbueno.luziachallenge.data.remote.dto.ChatCompletionRequestDto
import com.lucasbueno.luziachallenge.data.util.ChatCacheConfig
import com.lucasbueno.luziachallenge.domain.model.ChatMessage
import com.lucasbueno.luziachallenge.domain.model.MessageAuthor
import com.lucasbueno.luziachallenge.domain.repository.ChatRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

private const val TRANSCRIPTION_MODEL = "whisper-1"
private const val COMPLETION_MODEL = "gpt-4o-mini"
private const val AUDIO_MULTIPART_NAME = "file"

class ChatRepositoryImpl(
    private val service: OpenAiService,
    private val chatMessageDao: ChatMessageDao,
    private val cacheConfig: ChatCacheConfig,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ChatRepository {

    override fun observeMessages(): Flow<List<ChatMessage>> {
        return chatMessageDao.getMessages().map { entities ->
            entities.map { messageEntity ->
                messageEntity.toDomain()
            }
        }
    }

    override suspend fun saveMessage(message: ChatMessage) {
        withContext(dispatcher) {
            chatMessageDao.insertMessages(message.toEntity())
            enforceCacheLimit()
        }
    }

    override suspend fun saveMessages(messages: List<ChatMessage>) {
        withContext(dispatcher) {
            val entities = messages.map { message ->
                message.toEntity()
            }
            chatMessageDao.insertMessages(entities)
            enforceCacheLimit()
        }
    }

    override suspend fun clear() {
        withContext(dispatcher) { chatMessageDao.clear() }
    }

    override suspend fun transcribeAudio(audioFile: File): Result<String> {
        return runCatching {
            withContext(dispatcher) {
                val requestFile = audioFile.asRequestBody("audio/m4a".toMediaTypeOrNull())
                val body = MultipartBody.Part.createFormData(
                    AUDIO_MULTIPART_NAME,
                    audioFile.name,
                    requestFile
                )
                val modelBody: RequestBody = TRANSCRIPTION_MODEL
                    .toRequestBody("text/plain".toMediaTypeOrNull())
                service.transcribeAudio(body, modelBody).text
            }
        }
    }

    override suspend fun requestCompletion(history: List<ChatMessage>): Result<ChatMessage> {
        return runCatching {
            withContext(dispatcher) {
                val messages = history.map { message ->
                    ChatCompletionMessageDto(
                        role = message.author.toApiRole(),
                        content = message.content
                    )
                }
                val request = ChatCompletionRequestDto(
                    model = COMPLETION_MODEL,
                    messages = messages
                )
                val response = service.createChatCompletion(request)
                val assistantMessage = response.choices.firstOrNull()?.message
                    ?: throw IllegalStateException("Completion response did not include a message")
                ChatMessage(
                    author = MessageAuthor.ASSISTANT,
                    content = assistantMessage.content,
                    timestamp = System.currentTimeMillis()
                )
            }
        }
    }

    private suspend fun enforceCacheLimit() {
        val total = chatMessageDao.count()
        val overflow = total - cacheConfig.maxMessages
        if (overflow > 0) {
            chatMessageDao.deleteOldest(overflow)
        }
    }

    private fun MessageAuthor.toApiRole(): String = when (this) {
        MessageAuthor.USER -> "user"
        MessageAuthor.ASSISTANT -> "assistant"
        MessageAuthor.SYSTEM -> "system"
    }
}
