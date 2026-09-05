package com.example.thechatapp.ui.model

import com.example.thechatapp.domain.model.ChatItem
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

fun ChatItem.toPresentableChatItem(): PresentableChatItem = when (this) {
    is ChatItem.Message -> PresentableChatItem.MessageItem(
        id = this.id,
        content = this.content,
        timestamp = this.timestamp,
        hasCurrentUserSent = this.hasCurrentUserSent,
        isConsecutiveMessage = this.isConsecutiveMessage,
        isRead = this.isRead,
    )

    is ChatItem.TimeStampDivider -> PresentableChatItem.TimeStampDividerItem(
        timestamp = this.timestamp,
        dayOfWeek = this.timestamp.getDayOfWeek(),
        formattedTime = this.timestamp.getFormattedDate(),
    )
}

private fun Long.getDayOfWeek(): String {
    val dateTime = Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.currentSystemDefault())
    return dateTime.dayOfWeek.name.lowercase().replaceFirstChar { it.titlecase() }
}

private fun Long.getFormattedDate(): String {
    val dateTime = Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.currentSystemDefault())
    return dateTime.format(timeFormatter)
}

private val timeFormatter = LocalDateTime.Format {
    hour()
    chars(":")
    minute()
}
