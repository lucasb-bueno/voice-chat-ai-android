package com.lucasbueno.luziachallenge.domain.usecase

import com.lucasbueno.luziachallenge.domain.repository.ChatRepository
import java.io.File

class TranscribeAudioUseCase(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(audioFile: File): Result<String> {
        return repository.transcribeAudio(audioFile)
    }
}
