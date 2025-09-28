package com.lucasbueno.luziachallenge.di

import android.content.Context
import androidx.room.Room
import com.lucasbueno.luziachallenge.BuildConfig
import com.lucasbueno.luziachallenge.data.local.database.ChatDatabase
import com.lucasbueno.luziachallenge.data.repository.ChatRepositoryImpl
import com.lucasbueno.luziachallenge.data.util.ChatCacheConfig
import com.lucasbueno.luziachallenge.domain.repository.ChatRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val DATABASE_NAME = "chat-history.db"

val dataModule = module {
    single { provideDatabase(context = androidContext()) }
    single { get<ChatDatabase>().chatMessageDao() }
    single { ChatCacheConfig(maxMessages = BuildConfig.CHAT_CACHE_LIMIT) }
    single<ChatRepository> { ChatRepositoryImpl(get(), get(), get()) }
}

fun provideDatabase(context: Context): ChatDatabase {
    return Room.databaseBuilder(
        context,
        ChatDatabase::class.java,
        DATABASE_NAME
    ).build()
}
