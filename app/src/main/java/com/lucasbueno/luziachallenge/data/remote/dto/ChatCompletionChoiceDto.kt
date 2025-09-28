package com.lucasbueno.luziachallenge.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChatCompletionChoiceDto(
    val index: Int,
    val message: ChatCompletionMessageDto
)
