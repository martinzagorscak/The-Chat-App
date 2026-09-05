package com.example.thechatapp.ui.screens.chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thechatapp.R
import com.example.thechatapp.ui.model.PresentableChatItem
import com.example.thechatapp.ui.screens.chat.components.ChatFooter
import com.example.thechatapp.ui.screens.chat.components.ChatTimeStampDividerItem
import com.example.thechatapp.ui.screens.chat.components.ChatTopBar
import com.example.thechatapp.ui.screens.chat.components.EmptyChatState
import com.example.thechatapp.ui.screens.chat.components.LoadingIndicator
import com.example.thechatapp.ui.screens.chat.components.MessageBubble
import com.example.thechatapp.ui.theme.padding050
import com.example.thechatapp.ui.theme.padding200
import com.example.thechatapp.ui.theme.padding300
import com.example.thechatapp.ui.theme.padding400
import com.example.thechatapp.ui.theme.warningColor

private val defaultMessagePadding = padding050
private val additionalMessagePadding = padding200
private val messageBubbleMaxWidth = 300.dp
private val messageBubbleMinWidth = 50.dp

@Composable
fun ChatScreen(
    userViewState: ChatViewState.UserViewState,
    messagesViewState: ChatViewState.MessagesViewState,
    messageInputViewState: ChatViewState.MessageInputViewState,
    showClearChatDialog: Boolean,
    callbacks: ChatScreenCallbacks,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            Column {
                ChatTopBar(
                    userImageUrl = userViewState.profileImageUrl,
                    userName = userViewState.name,
                    onBackClick = callbacks.onBackClick,
                    onMoreOptionsClick = callbacks.onMoreOptionsClick.takeIf { messagesViewState is ChatViewState.MessagesViewState.Loaded && messagesViewState.messages.isNotEmpty() },
                )
                HorizontalDivider(
                    color = Color.Transparent,
                    modifier = Modifier.shadow(DividerDefaults.Thickness)
                )
            }
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .consumeWindowInsets(WindowInsets.navigationBars)
                    .imePadding()
            ) {
                HorizontalDivider(
                    color = Color.Transparent,
                    modifier = Modifier.shadow(DividerDefaults.Thickness)
                )
                ChatFooter(
                    message = messageInputViewState.message,
                    onMessageInputChange = callbacks.onMessageInputChanged,
                    onSendMessageClick = callbacks.onSendMessage
                )
            }
        },
        modifier = modifier.fillMaxSize(),
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            when (messagesViewState) {
                is ChatViewState.MessagesViewState.Loaded -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(defaultMessagePadding),
                        contentPadding = PaddingValues(padding400),
                        reverseLayout = true,
                    ) {
                        items(messagesViewState.messages) { chatItem ->
                            when (chatItem) {
                                is PresentableChatItem.MessageItem -> {
                                    Row(
                                        horizontalArrangement = if (chatItem.hasCurrentUserSent) Arrangement.End else Arrangement.Start,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        MessageBubble(
                                            text = chatItem.content,
                                            isMessageFromCurrentUser = chatItem.hasCurrentUserSent,
                                            isMessageRead = chatItem.isRead,
                                            modifier = Modifier
                                                .widthIn(max = messageBubbleMaxWidth, min = messageBubbleMinWidth)
                                                // just top padding for consecutive messages because the precursor in above it
                                                .padding(top = additionalMessagePadding.takeIf { chatItem.isConsecutiveMessage.not() } ?: 0.dp)
                                        )
                                    }
                                }

                                is PresentableChatItem.TimeStampDividerItem -> {
                                    ChatTimeStampDividerItem(
                                        dayOfWeek = chatItem.dayOfWeek,
                                        time = chatItem.formattedTime,
                                        modifier = Modifier.padding(vertical = padding300)
                                    )
                                }
                            }
                        }
                    }
                }

                ChatViewState.MessagesViewState.Empty -> {
                    AlignInTheMiddle {
                        EmptyChatState()
                    }
                }

                ChatViewState.MessagesViewState.Loading -> {
                    AlignInTheMiddle {
                        LoadingIndicator()
                    }
                }
            }
        }
    }

    if (showClearChatDialog) {
        AlertDialog(
            title = { Text(text = stringResource(R.string.clear_chat_dialog_title)) },
            text = { Text(text = stringResource(R.string.clear_chat_dialog_message)) },
            confirmButton = {
                Text(
                    text = stringResource(R.string.clear_chat_dialog_positive_button),
                    color = warningColor,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable(onClick = callbacks.onClearChatClick)
                        .padding(padding200)
                )
            },
            dismissButton = {
                Text(
                    text = stringResource(R.string.clear_chat_dialog_negative_button),
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable(onClick = callbacks.onMoreOptionsDismiss)
                        .padding(padding200)
                )
            },
            onDismissRequest = callbacks.onMoreOptionsDismiss,
        )
    }
}

@Composable
private fun AlignInTheMiddle(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        content()
    }
}

@Preview
@Composable
private fun ChatScreenPreview() {
    ChatScreen(
        userViewState = ChatViewState.UserViewState.INITIAL,
        messagesViewState = ChatViewState.MessagesViewState.Empty,
        messageInputViewState = ChatViewState.MessageInputViewState.INITIAL,
        showClearChatDialog = false,
        callbacks = ChatScreenCallbacks(
            onMessageInputChanged = {},
            onSendMessage = {},
            onClearChatClick = {},
            onBackClick = {},
            onMoreOptionsClick = {},
            onMoreOptionsDismiss = {},
        )
    )
}

data class ChatScreenCallbacks(
    val onMessageInputChanged: (String) -> Unit,
    val onSendMessage: () -> Unit,
    val onClearChatClick: () -> Unit,
    val onBackClick: () -> Unit,
    val onMoreOptionsClick: () -> Unit,
    val onMoreOptionsDismiss: () -> Unit,
)
