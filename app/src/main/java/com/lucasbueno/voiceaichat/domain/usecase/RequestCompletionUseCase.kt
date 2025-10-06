package com.lucasbueno.voiceaichat.domain.usecase

import com.lucasbueno.voiceaichat.domain.model.ChatMessage
import com.lucasbueno.voiceaichat.domain.repository.ChatRepository

class RequestCompletionUseCase(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(history: List<ChatMessage>): Result<ChatMessage> {
        return repository.requestCompletion(history)
    }
}
