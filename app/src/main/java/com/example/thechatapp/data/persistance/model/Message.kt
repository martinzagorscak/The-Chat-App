package com.example.thechatapp.data.persistance.model

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity
data class Message(
    @PrimaryKey val id: String,
    val chatRoomId: String, // same as participant (other non-logged-in) user id
    val senderId: String,
    val receiverId: String,
    val content: String,
    val timestamp: Long,
)
