package com.example.thechatapp.data.persistance

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.thechatapp.data.persistance.model.Message

@Dao
interface MessageDao {

    @Query("SELECT * FROM Messages WHERE chatRoomId = :chatRoomId ORDER BY timestamp DESC")
    fun getMessagesPagingSource(chatRoomId: String): PagingSource<Int, Message>

    @Insert
    suspend fun insertMessage(message: Message)

    @Query("DELETE FROM Messages WHERE chatRoomId = :chatRoomId")
    suspend fun clearChat(chatRoomId: String)
}
