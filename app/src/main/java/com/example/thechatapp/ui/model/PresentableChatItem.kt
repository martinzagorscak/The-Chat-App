package com.example.thechatapp.ui.model

sealed class PresentableChatItem {
    data class PresentableMessageItem(
        val id: String,
        val content: String,
        val timestamp: Long,
        val hasCurrentUserSent: Boolean,
        val isConsecutiveMessage: Boolean,
        val isRead: Boolean,
    ) : PresentableChatItem()

    data class PresentableTimeStampDividerItem(
        val timestamp: Long,
        val dayOfWeek: String,
        val formattedTime: String,
    ) : PresentableChatItem()
}
