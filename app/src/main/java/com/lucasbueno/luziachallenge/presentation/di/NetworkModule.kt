package com.lucasbueno.luziachallenge.presentation.di

import com.lucasbueno.luziachallenge.data.remote.api.OpenAiService
import com.lucasbueno.luziachallenge.data.remote.interceptor.AuthInterceptor
import com.lucasbueno.luziachallenge.data.remote.interceptor.BuildConfigTokenProvider
import com.lucasbueno.luziachallenge.data.remote.interceptor.TokenProvider
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val OPEN_AI_BASE_URL = "https://api.openai.com"

val networkModule = module {
    single<TokenProvider> { BuildConfigTokenProvider() }
    single {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(get()))
            .addInterceptor(logging)
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(OPEN_AI_BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single { get<Retrofit>().create(OpenAiService::class.java) }
}
