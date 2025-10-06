package com.lucasbueno.voiceaichat.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lucasbueno.voiceaichat.data.local.entity.ChatMessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatMessageDao {
    @Query("SELECT * FROM chat_messages ORDER BY timestamp ASC")
    fun getMessages(): Flow<List<ChatMessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(message: ChatMessageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(messages: List<ChatMessageEntity>)

    @Query("DELETE FROM chat_messages")
    suspend fun clear()

    @Query("SELECT COUNT(*) FROM chat_messages")
    suspend fun count(): Int

    @Query(
        "DELETE FROM chat_messages WHERE id IN (" +
            "SELECT id FROM chat_messages ORDER BY timestamp ASC LIMIT :rows" +
            ")"
    )
    suspend fun deleteOldest(rows: Int)
}
