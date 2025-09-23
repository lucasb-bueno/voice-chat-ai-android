package com.lucasbueno.luziachallenge.data.remote.interceptor

import com.lucasbueno.luziachallenge.BuildConfig

class BuildConfigTokenProvider : TokenProvider {
    override fun provideToken(): String = BuildConfig.OPENAI_API_KEY
}
