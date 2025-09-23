package com.lucasbueno.luziachallenge.data.remote.interceptor

interface TokenProvider {
    fun provideToken(): String
}
