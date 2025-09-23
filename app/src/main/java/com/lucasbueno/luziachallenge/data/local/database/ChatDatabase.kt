package com.lucasbueno.luziachallenge.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lucasbueno.luziachallenge.data.local.dao.ChatMessageDao
import com.lucasbueno.luziachallenge.data.local.entity.ChatMessageEntity

@Database(
    entities = [ChatMessageEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ChatDatabase : RoomDatabase() {
    abstract fun chatMessageDao(): ChatMessageDao
}
