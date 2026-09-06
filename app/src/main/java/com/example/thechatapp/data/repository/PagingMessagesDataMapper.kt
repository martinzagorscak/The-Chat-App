package com.example.thechatapp.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.example.thechatapp.data.persistance.model.Message
import com.example.thechatapp.data.persistance.model.toChatMessageItem
import com.example.thechatapp.domain.model.ChatItem
import kotlin.math.abs

private const val CONSECUTIVE_MESSAGE_WINDOW_MILLIS = 20_000L

fun PagingData<Message>.toChatItems(): PagingData<ChatItem.Message> {
    var previousMessage: Message? = null

    return map { message ->
        val isConsecutiveMessage = message.isConsecutiveMessage(previousMessage)
        previousMessage = message

        message.toChatMessageItem(isConsecutiveMessage = isConsecutiveMessage)
    }
}

private fun Message.isConsecutiveMessage(previousMessage: Message?): Boolean =
    previousMessage != null &&
        previousMessage.senderId == senderId &&
        abs(timestamp - previousMessage.timestamp) <= CONSECUTIVE_MESSAGE_WINDOW_MILLIS
