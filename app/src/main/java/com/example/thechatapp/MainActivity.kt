package com.example.thechatapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.thechatapp.ui.screens.chat.ChatScreen
import com.example.thechatapp.ui.screens.chat.ChatScreenCallbacks
import com.example.thechatapp.ui.screens.chat.ChatViewModel
import com.example.thechatapp.ui.screens.chat.ChatViewState
import com.example.thechatapp.ui.theme.TheChatAppTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheChatAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel = koinViewModel<ChatViewModel>()
                    val userViewState by viewModel.userViewState().collectAsState(initial = ChatViewState.UserViewState.INITIAL)
                    val messagesViewState by viewModel.messagesViewState().collectAsState(initial = ChatViewState.MessagesViewState.INITIAL)
                    val messageInputViewState by viewModel.messageInputViewState()
                        .collectAsState(initial = ChatViewState.MessageInputViewState.INITIAL)
                    var showClearChatDialog by remember { mutableStateOf(false) }

                    ChatScreen(
                        userViewState = userViewState,
                        messagesViewState = messagesViewState,
                        messageInputViewState = messageInputViewState,
                        showClearChatDialog = showClearChatDialog,
                        callbacks = remember {
                            ChatScreenCallbacks(
                                onMessageInputChanged = viewModel::onMessageInputChanged,
                                onSendMessage = viewModel::sendMessage,
                                onClearChatClick = {
                                    showClearChatDialog = false
                                    viewModel.clearChat()
                                },
                                onBackClick = { this.onBackPressed() },
                                onMoreOptionsClick = { showClearChatDialog = true },
                                onMoreOptionsDismiss = { showClearChatDialog = false },
                            )
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
