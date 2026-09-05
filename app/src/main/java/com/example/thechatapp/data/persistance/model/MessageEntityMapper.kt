package com.example.thechatapp.data.persistance.model

import com.example.thechatapp.domain.model.ChatItem

fun Message.toChatMessageItem(isConsecutiveMessage: Boolean): ChatItem.Message {
    return ChatItem.Message(
        id = this.id,
        content = this.content,
        timestamp = this.timestamp,
        hasCurrentUserSent = this.senderId != this.chatRoomId, // chatRoomId is the other user's id
        isConsecutiveMessage = isConsecutiveMessage,
        isRead = true, // mocked, since the app is offline
    )
}
