package com.lucasbueno.luziachallenge.domain.usecase

import com.lucasbueno.luziachallenge.domain.repository.ChatRepository

class ClearChatUseCase(
    private val repository: ChatRepository
) {
    suspend operator fun invoke() {
        repository.clear()
    }
}
