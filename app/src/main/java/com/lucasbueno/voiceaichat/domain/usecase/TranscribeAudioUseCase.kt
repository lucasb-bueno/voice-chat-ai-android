package com.lucasbueno.voiceaichat.domain.usecase

import com.lucasbueno.voiceaichat.domain.repository.ChatRepository
import java.io.File

class TranscribeAudioUseCase(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(audioFile: File): Result<String> {
        return repository.transcribeAudio(audioFile)
    }
}
