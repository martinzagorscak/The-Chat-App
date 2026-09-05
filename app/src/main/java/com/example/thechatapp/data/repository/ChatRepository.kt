package com.example.thechatapp.data.repository

import com.example.thechatapp.data.persistance.MessageDao
import com.example.thechatapp.data.persistance.model.Message
import com.example.thechatapp.data.persistance.model.toChatMessageItem
import com.example.thechatapp.domain.model.ChatItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import kotlin.math.abs
import kotlin.time.Duration.Companion.milliseconds

private val ONE_SECOND_MILLIS = 1000L
private val CONSECUTIVE_MESSAGE_WINDOW_MILLIS = 20_000L
private val ONE_HOUR_MILLIS = 3600000L

interface ChatRepository {
    fun chatMessages(userId: String): Flow<List<ChatItem>>

    suspend fun sendMessage(
        userId: String,
        receiverId: String,
        message: String
    )

    suspend fun clearChat(userId: String)
}

@OptIn(ExperimentalCoroutinesApi::class)
internal class ChatRepositoryImpl(
    private val messageDao: MessageDao,
) : ChatRepository {

    override fun chatMessages(userId: String): Flow<List<ChatItem>> = messageDao.getMessages(chatRoomId = userId).mapLatest { messages ->
        val result = mutableListOf<ChatItem>()

        messages.forEachIndexed { index, messageEntity ->
            val previousMessage = messages.getOrNull(index + 1)
            val isConsecutiveMessage = previousMessage != null &&
                previousMessage.senderId == messageEntity.senderId &&
                abs(messageEntity.timestamp - previousMessage.timestamp) <= CONSECUTIVE_MESSAGE_WINDOW_MILLIS

            result.add(messageEntity.toChatMessageItem(isConsecutiveMessage = isConsecutiveMessage))

            // Add TimeStampDivider if no previous message or more than an hour passed
            val shouldAddDivider = previousMessage == null ||
                abs(messageEntity.timestamp - previousMessage.timestamp) > ONE_HOUR_MILLIS

            if (shouldAddDivider) {
                result.add(ChatItem.TimeStampDivider(messageEntity.timestamp))
            }
        }

        result
    }

    override suspend fun sendMessage(
        userId: String,
        receiverId: String,
        message: String
    ) {
        val timeStamp = System.currentTimeMillis()
        messageDao.insertMessage(
            message = Message(
                id = timeStamp.toString(),
                chatRoomId = receiverId,
                senderId = userId,
                receiverId = receiverId,
                content = message,
                timestamp = timeStamp,
            )
        )

        replyWithBot(
            senderId = receiverId,
            receiverId = userId,
            message = "This is a mocked bot response to: $message",
        )
    }

    private suspend fun replyWithBot(
        senderId: String,
        receiverId: String,
        message: String,
    ) {
        delay(ONE_SECOND_MILLIS.milliseconds) // simulate bot response delay

        val timeStamp = System.currentTimeMillis()

        messageDao.insertMessage(
            message = Message(
                id = timeStamp.toString(),
                chatRoomId = senderId,
                senderId = senderId,
                receiverId = receiverId,
                content = message,
                timestamp = timeStamp,
            )
        )
    }

    override suspend fun clearChat(userId: String) = messageDao.clearChat(chatRoomId = userId)
}
