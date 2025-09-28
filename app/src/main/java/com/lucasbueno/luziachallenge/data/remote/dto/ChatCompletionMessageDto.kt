package com.lucasbueno.luziachallenge.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChatCompletionMessageDto(
    val role: String,
    val content: String
)
