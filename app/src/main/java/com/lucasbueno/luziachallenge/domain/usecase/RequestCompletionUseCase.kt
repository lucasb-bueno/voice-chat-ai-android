package com.lucasbueno.luziachallenge.domain.usecase

import com.lucasbueno.luziachallenge.domain.model.ChatMessage
import com.lucasbueno.luziachallenge.domain.repository.ChatRepository

class RequestCompletionUseCase(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(history: List<ChatMessage>): Result<ChatMessage> {
        return repository.requestCompletion(history)
    }
}
