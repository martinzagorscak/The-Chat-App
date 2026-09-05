package com.example.thechatapp.ui.screens.chat

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.thechatapp.domain.model.ChatItem
import com.example.thechatapp.domain.usecases.ClearChatUseCase
import com.example.thechatapp.domain.usecases.GetChatMessagesUseCase
import com.example.thechatapp.domain.usecases.GetUserProfileUseCase
import com.example.thechatapp.domain.usecases.SendMessageUseCase
import com.example.thechatapp.ui.model.PresentableChatItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

val EMPTY = ""
private val BOT_USER_ID = "bot_user_id"

@OptIn(ExperimentalCoroutinesApi::class)
internal class ChatViewModelImpl(
    gerUserProfileUseCase: GetUserProfileUseCase,
    getChatMessagesUseCase: GetChatMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val clearChatUseCase: ClearChatUseCase,
) : ChatViewModel() {

    private val userProfile = gerUserProfileUseCase(userId = BOT_USER_ID)
    private val chatMessages = getChatMessagesUseCase(userId = BOT_USER_ID)
    private val messageInput = MutableStateFlow<String>(EMPTY)

    override fun userViewState(): Flow<ChatViewState.UserViewState> = userProfile.mapLatest {
        ChatViewState.UserViewState(
            id = it.id,
            name = it.name,
            profileImageUrl = it.imageUrl ?: EMPTY.also {
                Log.w("ChatViewModelImpl", "User profile image URL is null")
            },
        )
    }

    override fun messagesViewState(): Flow<ChatViewState.MessagesViewState> = chatMessages.mapLatest { messages ->
        if (messages.isNotEmpty()) {
            ChatViewState.MessagesViewState.Loaded(
                messages = messages.map { chatItem ->
                    when (chatItem) {
                        is ChatItem.Message -> {
                            PresentableChatItem.PresentableMessageItem(
                                id = chatItem.id,
                                content = chatItem.content,
                                timestamp = chatItem.timestamp,
                                hasCurrentUserSent = chatItem.hasCurrentUserSent,
                                isConsecutiveMessage = chatItem.isConsecutiveMessage,
                                isRead = chatItem.isRead,
                            )
                        }

                        is ChatItem.TimeStampDivider -> {
                            PresentableChatItem.PresentableTimeStampDividerItem(
                                timestamp = chatItem.timestamp,
                                dayOfWeek = chatItem.timestamp.getDayOfWeek(),
                                formattedTime = chatItem.timestamp.getFormattedDate(),
                            )
                        }
                    }
                }
            )
        } else {
            ChatViewState.MessagesViewState.Empty
        }
    }

    override fun messageInputViewState(): Flow<ChatViewState.MessageInputViewState> = messageInput.mapLatest { message ->
        ChatViewState.MessageInputViewState(
            message = message,
            isSendButtonEnabled = message.isNotBlank(),
        )
    }

    override fun onMessageInputChanged(message: String) = messageInput.update { message }

    override fun sendMessage() {
        viewModelScope.launch(Dispatchers.Default) {
            val message = messageInputViewState().first().message
            messageInput.update { EMPTY }
            sendMessageUseCase(message = message)
        }
    }

    override fun clearChat() {
        viewModelScope.launch(Dispatchers.Default) {
            clearChatUseCase(userId = BOT_USER_ID)
        }
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
}
