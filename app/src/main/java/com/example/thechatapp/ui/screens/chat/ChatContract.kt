package com.example.thechatapp.ui.screens.chat

import androidx.lifecycle.ViewModel
import com.example.thechatapp.ui.model.PresentableChatItem
import kotlinx.coroutines.flow.Flow

sealed class ChatViewState {
    data class UserViewState(
        val id: String,
        val name: String,
        val profileImageUrl: String,
    ) : ChatViewState() {
        companion object {
            val INITIAL = UserViewState(
                id = EMPTY,
                name = EMPTY,
                profileImageUrl = EMPTY,
            )
        }
    }

    sealed class MessagesViewState : ChatViewState() {
        data class Loaded(val messages: List<PresentableChatItem>) : MessagesViewState()
        data object Empty : MessagesViewState()
        data object Loading : MessagesViewState()
    }

    data class MessageInputViewState(
        val message: String,
        val isSendButtonEnabled: Boolean,
    ) : ChatViewState() {
        companion object {
            val INITIAL = MessageInputViewState(
                message = EMPTY,
                isSendButtonEnabled = false,
            )
        }
    }
}

abstract class ChatViewModel : ViewModel() {
    abstract fun userViewState(): Flow<ChatViewState.UserViewState>
    abstract fun messagesViewState(): Flow<ChatViewState.MessagesViewState>
    abstract fun messageInputViewState(): Flow<ChatViewState.MessageInputViewState>
    abstract fun onMessageInputChanged(message: String)
    abstract fun sendMessage()
    abstract fun clearChat()
}
