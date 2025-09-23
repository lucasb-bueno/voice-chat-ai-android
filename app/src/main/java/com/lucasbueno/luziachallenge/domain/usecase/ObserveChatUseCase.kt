package com.lucasbueno.luziachallenge.domain.usecase

import com.lucasbueno.luziachallenge.domain.model.ChatMessage
import com.lucasbueno.luziachallenge.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow

class ObserveChatUseCase(
    private val repository: ChatRepository
) {
    operator fun invoke(): Flow<List<ChatMessage>> = repository.observeMessages()
}
