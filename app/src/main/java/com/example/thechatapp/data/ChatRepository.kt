package com.example.thechatapp.data

import com.example.thechatapp.domain.model.ChatItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlin.time.Duration.Companion.milliseconds

private val ONE_SECOND = 1000L // in millis

interface ChatRepository {
    fun chatMessages(userId: String): Flow<List<ChatItem>>
    suspend fun sendMessage(message: String)
    suspend fun clearChat(userId: String)
}

internal class ChatRepositoryImpl(
    // TODO
) : ChatRepository {

    val mockedChatMessages = MutableStateFlow<List<ChatItem>>(
        listOf(
            ChatItem.Message(
                id = "1",
                content = "Hello",
                timestamp = 1788567447593L,
                hasCurrentUserSent = true,
                isConsecutiveMessage = false,
                isRead = true,
            ),
            ChatItem.Message(
                id = "2",
                content = "Hi there!",
                timestamp = 1788567444411L,
                hasCurrentUserSent = false,
                isConsecutiveMessage = false,
                isRead = true,
            ),
            ChatItem.TimeStampDivider(
                timestamp = 1788567444411L,
            ),
        )
    )

    override fun chatMessages(userId: String): Flow<List<ChatItem>> = mockedChatMessages

    override suspend fun sendMessage(message: String) {
        mockedChatMessages.update { currentMessages ->
            val newMessage = ChatItem.Message(
                id = (currentMessages.size + 1).toString(),
                content = message,
                timestamp = System.currentTimeMillis(),
                hasCurrentUserSent = true,
                isConsecutiveMessage = true, // todo calculate
                isRead = true, // mocked since the app is offline
            )
            listOf(newMessage) + currentMessages
        }

        replyWithBot("This is a mocked bot response to: $message")
    }

    private suspend fun replyWithBot(message: String) {
        delay(ONE_SECOND.milliseconds) // simulate bot response delay

        val currentTimeMillis = System.currentTimeMillis()
        val mockedBotMessage = ChatItem.Message(
            id = currentTimeMillis.toString(),
            content = message,
            timestamp = currentTimeMillis,
            hasCurrentUserSent = false,
            isConsecutiveMessage = false, // todo calculate
            isRead = true, // mocked since the app is offline
        )

        mockedChatMessages.update { currentMessages ->
            listOf(mockedBotMessage) + currentMessages
        }
    }

    override suspend fun clearChat(userId: String) = mockedChatMessages.update { emptyList() }
}
