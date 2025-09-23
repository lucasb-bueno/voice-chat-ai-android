package com.lucasbueno.luziachallenge.presentation.di

import com.lucasbueno.luziachallenge.domain.usecase.ClearChatUseCase
import com.lucasbueno.luziachallenge.domain.usecase.ObserveChatUseCase
import com.lucasbueno.luziachallenge.domain.usecase.RequestCompletionUseCase
import com.lucasbueno.luziachallenge.domain.usecase.SaveMessageUseCase
import com.lucasbueno.luziachallenge.domain.usecase.SpeakTextUseCase
import com.lucasbueno.luziachallenge.domain.usecase.TranscribeAudioUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { ObserveChatUseCase(get()) }
    factory { SaveMessageUseCase(get()) }
    factory { TranscribeAudioUseCase(get()) }
    factory { RequestCompletionUseCase(get()) }
    factory { ClearChatUseCase(get()) }
    single { SpeakTextUseCase(get()) }
}
