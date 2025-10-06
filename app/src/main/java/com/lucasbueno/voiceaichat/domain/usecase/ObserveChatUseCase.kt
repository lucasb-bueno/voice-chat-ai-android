package com.lucasbueno.voiceaichat.domain.usecase

import com.lucasbueno.voiceaichat.domain.model.ChatMessage
import com.lucasbueno.voiceaichat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow

class ObserveChatUseCase(
    private val repository: ChatRepository
) {
    operator fun invoke(): Flow<List<ChatMessage>> = repository.observeMessages()
}
