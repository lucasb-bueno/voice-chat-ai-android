package com.lucasbueno.luziachallenge.presentation.di

import com.lucasbueno.luziachallenge.data.audio.AndroidAudioRecorder
import com.lucasbueno.luziachallenge.data.tts.AndroidTextToSpeechEngine
import com.lucasbueno.luziachallenge.domain.audio.AudioRecorder
import com.lucasbueno.luziachallenge.domain.voice.VoiceSynthesizer
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val platformModule = module {
    single<AudioRecorder> { AndroidAudioRecorder(androidContext()) }
    single<VoiceSynthesizer> { AndroidTextToSpeechEngine(androidContext()) }
}
