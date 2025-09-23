package com.lucasbueno.luziachallenge.domain.usecase

import com.lucasbueno.luziachallenge.domain.model.ChatMessage
import com.lucasbueno.luziachallenge.domain.repository.ChatRepository

class SaveMessageUseCase(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(message: ChatMessage) {
        repository.saveMessage(message)
    }
}
