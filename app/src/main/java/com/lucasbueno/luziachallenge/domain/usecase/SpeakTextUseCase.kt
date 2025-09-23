package com.lucasbueno.luziachallenge.domain.usecase

import com.lucasbueno.luziachallenge.domain.voice.VoiceSynthesizer

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
