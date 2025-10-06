package com.lucasbueno.voiceaichat.data.remote.interceptor

import com.lucasbueno.voiceaichat.BuildConfig

class BuildConfigTokenProvider : TokenProvider {
    override fun provideToken(): String = BuildConfig.OPENAI_API_KEY
}
