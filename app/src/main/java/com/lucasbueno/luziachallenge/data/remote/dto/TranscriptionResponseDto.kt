package com.lucasbueno.luziachallenge.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TranscriptionResponseDto(
    val text: String
)
