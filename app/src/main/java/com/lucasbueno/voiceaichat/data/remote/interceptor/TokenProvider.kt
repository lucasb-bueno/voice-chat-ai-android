package com.lucasbueno.voiceaichat.data.remote.interceptor

interface TokenProvider {
    fun provideToken(): String
}
