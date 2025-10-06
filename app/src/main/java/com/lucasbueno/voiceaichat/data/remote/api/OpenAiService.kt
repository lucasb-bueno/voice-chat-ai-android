package com.lucasbueno.voiceaichat.data.remote.api

import com.lucasbueno.voiceaichat.data.remote.dto.ChatCompletionRequestDto
import com.lucasbueno.voiceaichat.data.remote.dto.ChatCompletionResponseDto
import com.lucasbueno.voiceaichat.data.remote.dto.TranscriptionResponseDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface OpenAiService {
    @Multipart
    @POST("/v1/audio/transcriptions")
    suspend fun transcribeAudio(
        @Part file: MultipartBody.Part,
        @Part("model") model: RequestBody
    ): TranscriptionResponseDto

    @POST("/v1/chat/completions")
    suspend fun createChatCompletion(
        @Body request: ChatCompletionRequestDto
    ): ChatCompletionResponseDto
}
