package com.lucasbueno.luziachallenge.di

import com.lucasbueno.luziachallenge.data.remote.api.OpenAiService
import com.lucasbueno.luziachallenge.data.remote.interceptor.AuthInterceptor
import com.lucasbueno.luziachallenge.data.remote.interceptor.BuildConfigTokenProvider
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val OPEN_AI_BASE_URL = "https://api.openai.com"

val networkModule = module {
    single {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(
                BuildConfigTokenProvider())
            )
            .addInterceptor(loggingInterceptor)
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(OPEN_AI_BASE_URL)
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create(
                Moshi.Builder().build())
            )
            .build()
    }
    single { get<Retrofit>().create(OpenAiService::class.java) }
}
