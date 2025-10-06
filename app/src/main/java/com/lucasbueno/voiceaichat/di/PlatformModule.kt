package com.lucasbueno.voiceaichat.di

import com.lucasbueno.voiceaichat.data.audio.AndroidAudioRecorder
import com.lucasbueno.voiceaichat.data.tts.AndroidTextToSpeechEngine
import com.lucasbueno.voiceaichat.domain.audio.AudioRecorder
import com.lucasbueno.voiceaichat.domain.voice.VoiceSynthesizer
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val platformModule = module {
    single<AudioRecorder> { AndroidAudioRecorder(androidContext()) }
    single<VoiceSynthesizer> { AndroidTextToSpeechEngine(androidContext()) }
}
