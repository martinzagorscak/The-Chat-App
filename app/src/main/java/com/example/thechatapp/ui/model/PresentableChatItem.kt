package com.example.thechatapp.ui.model

sealed class PresentableChatItem {
    data class MessageItem(
        val id: String,
        val content: String,
        val timestamp: Long,
        val hasCurrentUserSent: Boolean,
        val isConsecutiveMessage: Boolean,
        val isRead: Boolean,
    ) : PresentableChatItem()

    data class TimeStampDividerItem(
        val timestamp: Long,
        val dayOfWeek: String,
        val formattedTime: String,
    ) : PresentableChatItem()
}
