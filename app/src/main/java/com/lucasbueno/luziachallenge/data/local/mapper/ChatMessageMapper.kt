package com.lucasbueno.luziachallenge.data.local.mapper

import com.lucasbueno.luziachallenge.data.local.entity.ChatMessageEntity
import com.lucasbueno.luziachallenge.domain.model.ChatMessage
import com.lucasbueno.luziachallenge.domain.model.MessageAuthor

object ChatMessageMapper {
    fun toEntity(message: ChatMessage): ChatMessageEntity {
        return ChatMessageEntity(
            id = message.id ?: 0,
            author = message.author.name.lowercase(),
            content = message.content,
            timestamp = message.timestamp
        )
    }

    fun toDomain(entity: ChatMessageEntity): ChatMessage {
        val author = runCatching { MessageAuthor.valueOf(entity.author.uppercase()) }
            .getOrDefault(MessageAuthor.ASSISTANT)
        return ChatMessage(
            id = entity.id,
            author = author,
            content = entity.content,
            timestamp = entity.timestamp
        )
    }
}
