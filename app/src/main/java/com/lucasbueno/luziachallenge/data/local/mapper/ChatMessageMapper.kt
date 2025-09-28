package com.lucasbueno.luziachallenge.data.local.mapper

import com.lucasbueno.luziachallenge.data.local.entity.ChatMessageEntity
import com.lucasbueno.luziachallenge.domain.model.ChatMessage
import com.lucasbueno.luziachallenge.domain.model.MessageAuthor


fun ChatMessage.toEntity(): ChatMessageEntity {
    return ChatMessageEntity(
        id = this.id ?: 0,
        author = this.author.name.lowercase(),
        content = this.content,
        timestamp = this.timestamp
    )
}

fun ChatMessageEntity.toDomain(): ChatMessage {
    val author = runCatching { MessageAuthor.valueOf(this.author.uppercase()) }
        .getOrDefault(MessageAuthor.ASSISTANT)
    return ChatMessage(
        id = this.id,
        author = author,
        content = this.content,
        timestamp = this.timestamp
    )
}
