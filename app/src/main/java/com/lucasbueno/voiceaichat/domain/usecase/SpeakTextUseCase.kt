package com.lucasbueno.voiceaichat.domain.usecase

import com.lucasbueno.voiceaichat.domain.voice.VoiceSynthesizer

class SpeakTextUseCase(
    private val voiceSynthesizer: VoiceSynthesizer
) {
    suspend operator fun invoke(text: String) {
        voiceSynthesizer.speak(text)
    }

    fun stop() {
        voiceSynthesizer.stop()
    }

    fun release() {
        voiceSynthesizer.release()
    }
}
