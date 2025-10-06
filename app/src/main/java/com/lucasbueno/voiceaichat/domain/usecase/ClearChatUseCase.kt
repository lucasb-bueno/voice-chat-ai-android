package com.lucasbueno.voiceaichat.domain.usecase

import com.lucasbueno.voiceaichat.domain.repository.ChatRepository

class ClearChatUseCase(
    private val repository: ChatRepository
) {
    suspend operator fun invoke() {
        repository.clear()
    }
}
