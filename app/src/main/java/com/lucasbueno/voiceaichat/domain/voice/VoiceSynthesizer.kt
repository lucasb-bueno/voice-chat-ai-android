package com.lucasbueno.voiceaichat.domain.voice

interface VoiceSynthesizer {
    suspend fun speak(text: String)
    fun stop()
    fun release()
}
