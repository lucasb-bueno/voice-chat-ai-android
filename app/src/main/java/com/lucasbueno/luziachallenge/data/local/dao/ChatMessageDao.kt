package com.lucasbueno.luziachallenge.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lucasbueno.luziachallenge.data.local.entity.ChatMessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatMessageDao {
    @Query("SELECT * FROM chat_messages ORDER BY timestamp ASC")
    fun observeMessages(): Flow<List<ChatMessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(message: ChatMessageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(messages: List<ChatMessageEntity>)

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
