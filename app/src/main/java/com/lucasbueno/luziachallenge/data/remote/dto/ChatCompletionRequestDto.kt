package com.lucasbueno.luziachallenge.data.remote.dto

data class ChatCompletionRequestDto(
    val model: String,
    val messages: List<ChatCompletionMessageDto>,
    val temperature: Double? = null
)
