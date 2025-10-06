package com.lucasbueno.voiceaichat.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChatCompletionRequestDto(
    val model: String,
    val messages: List<ChatCompletionMessageDto>,
    val temperature: Double? = null
)
