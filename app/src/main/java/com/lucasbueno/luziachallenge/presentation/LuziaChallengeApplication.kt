package com.lucasbueno.luziachallenge.presentation

import android.app.Application
import com.lucasbueno.luziachallenge.di.dataModule
import com.lucasbueno.luziachallenge.di.domainModule
import com.lucasbueno.luziachallenge.di.networkModule
import com.lucasbueno.luziachallenge.di.platformModule
import com.lucasbueno.luziachallenge.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class LuziaChallengeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.INFO)
            androidContext(this@LuziaChallengeApplication)
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
