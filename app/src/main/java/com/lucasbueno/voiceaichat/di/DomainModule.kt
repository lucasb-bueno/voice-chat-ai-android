package com.lucasbueno.voiceaichat.di

import com.lucasbueno.voiceaichat.domain.usecase.ClearChatUseCase
import com.lucasbueno.voiceaichat.domain.usecase.ObserveChatUseCase
import com.lucasbueno.voiceaichat.domain.usecase.RequestCompletionUseCase
import com.lucasbueno.voiceaichat.domain.usecase.SaveMessageUseCase
import com.lucasbueno.voiceaichat.domain.usecase.SpeakTextUseCase
import com.lucasbueno.voiceaichat.domain.usecase.TranscribeAudioUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { ObserveChatUseCase(get()) }
    factory { SaveMessageUseCase(get()) }
    factory { TranscribeAudioUseCase(get()) }
    factory { RequestCompletionUseCase(get()) }
    factory { ClearChatUseCase(get()) }
    single { SpeakTextUseCase(get()) }
}
