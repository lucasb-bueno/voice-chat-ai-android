package com.lucasbueno.voiceaichat.domain.usecase

import com.lucasbueno.voiceaichat.domain.model.ChatMessage
import com.lucasbueno.voiceaichat.domain.repository.ChatRepository

class SaveMessageUseCase(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(message: ChatMessage) {
        repository.saveMessage(message)
    }
}
