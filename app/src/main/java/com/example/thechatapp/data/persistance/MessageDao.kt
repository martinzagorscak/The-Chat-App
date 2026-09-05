package com.example.thechatapp.data.persistance

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import com.example.thechatapp.data.persistance.model.Message
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {

    @Query("SELECT * FROM Message WHERE chatRoomId = :chatRoomId ORDER BY timestamp DESC")
    fun getMessages(chatRoomId: String): Flow<List<Message>>

    @Insert
    suspend fun insertMessage(message: Message)

    @Query("DELETE FROM Message WHERE chatRoomId = :chatRoomId")
    suspend fun clearChat(chatRoomId: String)
}
