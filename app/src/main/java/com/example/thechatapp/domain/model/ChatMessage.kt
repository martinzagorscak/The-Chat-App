package com.example.thechatapp.domain.model

sealed class ChatItem {
    data class Message(
        val id: String,
        val content: String,
        val timestamp: Long,
        val hasCurrentUserSent: Boolean,
        val isConsecutiveMessage: Boolean,
        val isRead: Boolean,
    ) : ChatItem()

    data class TimeStampDivider(val timestamp: Long) : ChatItem()
}
