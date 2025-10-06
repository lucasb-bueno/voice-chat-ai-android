package com.lucasbueno.voiceaichat.presentation

import android.app.Application
import com.lucasbueno.voiceaichat.di.dataModule
import com.lucasbueno.voiceaichat.di.domainModule
import com.lucasbueno.voiceaichat.di.networkModule
import com.lucasbueno.voiceaichat.di.platformModule
import com.lucasbueno.voiceaichat.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class VoiceAIChatApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.INFO)
            androidContext(this@VoiceAIChatApplication)
            modules(
                listOf(
                    networkModule,
                    dataModule,
                    domainModule,
                    platformModule,
                    presentationModule
                )
            )
        }
    }
}
