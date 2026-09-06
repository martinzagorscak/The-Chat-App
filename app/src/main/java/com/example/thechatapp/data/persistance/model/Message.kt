package com.example.thechatapp.data.persistance.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Messages",
    indices = [Index(value = ["timestamp"], orders = [Index.Order.DESC])]
)
data class Message(
    @PrimaryKey val id: String,
    val chatRoomId: String, // same as participant (other non-logged-in) user id
    val senderId: String,
    val receiverId: String,
    val content: String,
    val timestamp: Long,
)
