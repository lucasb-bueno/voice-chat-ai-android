package com.lucasbueno.voiceaichat.domain.repository

import com.lucasbueno.voiceaichat.domain.model.ChatMessage
import kotlinx.coroutines.flow.Flow
import java.io.File

interface ChatRepository {
    fun observeMessages(): Flow<List<ChatMessage>>
    suspend fun saveMessage(message: ChatMessage)
    suspend fun saveMessages(messages: List<ChatMessage>)
    suspend fun clear()
    suspend fun transcribeAudio(audioFile: File): Result<String>
    suspend fun requestCompletion(history: List<ChatMessage>): Result<ChatMessage>
}
