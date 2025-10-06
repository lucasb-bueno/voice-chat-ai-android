package com.lucasbueno.voiceaichat.domain.model

data class ChatMessage(
    val id: Long? = null,
    val author: MessageAuthor,
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)

enum class MessageAuthor {
    USER,
    ASSISTANT,
    SYSTEM
}
