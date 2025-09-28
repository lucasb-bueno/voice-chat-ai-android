package com.lucasbueno.luziachallenge.di

import com.lucasbueno.luziachallenge.presentation.chat.ChatViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        ChatViewModel(
            observeChatUseCase = get(),
            saveMessageUseCase = get(),
            transcribeAudioUseCase = get(),
            requestCompletionUseCase = get(),
            clearChatUseCase = get(),
            speakTextUseCase = get(),
            audioRecorder = get()
        )
    }
}
