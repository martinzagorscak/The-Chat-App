package com.example.thechatapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.insertSeparators
import com.example.thechatapp.data.persistance.MessageDao
import com.example.thechatapp.data.persistance.model.Message
import com.example.thechatapp.domain.model.ChatItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.math.abs
import kotlin.time.Duration.Companion.milliseconds

private const val ONE_SECOND_MILLIS = 1000L
private const val ONE_HOUR_MILLIS = 3600000L
private const val PAGE_SIZE = 30
private const val PREFETCH_DISTANCE = 10

interface ChatRepository {
    fun chatMessages(userId: String): Flow<PagingData<ChatItem>>

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

    override fun chatMessages(userId: String): Flow<PagingData<ChatItem>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = PREFETCH_DISTANCE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { messageDao.getMessagesPagingSource(chatRoomId = userId) }
        ).flow.map { pagingData ->
            pagingData
                .toChatItems()
                .insertSeparators { before: ChatItem.Message?, after: ChatItem.Message? ->
                    if (before == null) {
                        return@insertSeparators null
                    }

                    val shouldAddDivider = after == null ||
                        abs(before.timestamp - after.timestamp) > ONE_HOUR_MILLIS

                    ChatItem.TimeStampDivider(before.timestamp).takeIf { shouldAddDivider }
                }
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
