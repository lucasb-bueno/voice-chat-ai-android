package com.lucasbueno.luziachallenge.presentation

import android.app.Application
import com.lucasbueno.luziachallenge.presentation.di.dataModule
import com.lucasbueno.luziachallenge.presentation.di.domainModule
import com.lucasbueno.luziachallenge.presentation.di.networkModule
import com.lucasbueno.luziachallenge.presentation.di.platformModule
import com.lucasbueno.luziachallenge.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class LuziaChallengeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.INFO)
            androidContext(this@LuziaChallengeApp)
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
