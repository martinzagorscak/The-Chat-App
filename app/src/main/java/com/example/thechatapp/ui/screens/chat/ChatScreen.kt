package com.example.thechatapp.ui.screens.chat

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.thechatapp.ui.model.PresentableChatItem
import com.example.thechatapp.ui.screens.chat.components.AlignInTheMiddle
import com.example.thechatapp.ui.screens.chat.components.ChatFooter
import com.example.thechatapp.ui.screens.chat.components.ChatTimeStampDividerItem
import com.example.thechatapp.ui.screens.chat.components.ChatTopBar
import com.example.thechatapp.ui.screens.chat.components.ClearChatDialog
import com.example.thechatapp.ui.screens.chat.components.EmptyChatState
import com.example.thechatapp.ui.screens.chat.components.LoadingIndicator
import com.example.thechatapp.ui.screens.chat.components.MessageBubble
import com.example.thechatapp.ui.theme.padding050
import com.example.thechatapp.ui.theme.padding200
import com.example.thechatapp.ui.theme.padding300
import com.example.thechatapp.ui.theme.padding400
import kotlinx.coroutines.launch

private val defaultMessagePadding = padding050
private val additionalMessagePadding = padding200
private val messageBubbleMaxWidth = 300.dp
private val messageBubbleMinWidth = 50.dp
private const val FIRST_ITEM_INDEX = 0

@Composable
fun ChatScreen(
    userViewState: ChatViewState.UserViewState,
    messagesViewState: ChatViewState.MessagesViewState,
    messageInputViewState: ChatViewState.MessageInputViewState,
    showClearChatDialog: Boolean,
    callbacks: ChatScreenCallbacks,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val lazyPagingMessages = messagesViewState.pagingDataFlow.collectAsLazyPagingItems()
    val sourceRefreshState = lazyPagingMessages.loadState.source.refresh
    val isLoading by handleLoadingState(state = sourceRefreshState)
    val isEmpty = isLoading.not() &&
        sourceRefreshState is LoadState.NotLoading &&
        lazyPagingMessages.itemCount == 0

    Scaffold(
        topBar = {
            Column {
                ChatTopBar(
                    userImageUrl = userViewState.profileImageUrl,
                    userName = userViewState.name,
                    onBackClick = callbacks.onBackClick,
                    onMoreOptionsClick = callbacks.onMoreOptionsClick.takeIf { isLoading.not() && isEmpty.not() },
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
            when {
                isLoading -> {
                    AlignInTheMiddle {
                        LoadingIndicator()
                    }
                }

                isEmpty -> {
                    AlignInTheMiddle {
                        EmptyChatState()
                    }
                }

                else -> {
                    val lazyColumnState = rememberLazyListState()
                    LazyColumn(
                        state = lazyColumnState,
                        verticalArrangement = Arrangement.spacedBy(defaultMessagePadding),
                        contentPadding = PaddingValues(padding400),
                        reverseLayout = true,
                    ) {
                        items(count = lazyPagingMessages.itemCount) { index ->
                            lazyPagingMessages[index]?.let { chatItem ->
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

                    LaunchedEffect(Unit) {
                        scope.launch {
                            messagesViewState.scrollToBottomPublisher.collect {
                                lazyColumnState.scrollToItem(index = FIRST_ITEM_INDEX)
                            }
                        }
                    }
                }
            }
        }
    }

    if (showClearChatDialog) {
        ClearChatDialog(
            onConfirmButtonClick = callbacks.onClearChatClick,
            onDismissButtonClick = callbacks.onMoreOptionsDismiss
        )
    }
}

@Composable
private fun handleLoadingState(state: LoadState): State<Boolean> {
    var hasHandledInitialRefresh by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(state) {
        if (hasHandledInitialRefresh.not() && state !is LoadState.Loading) {
            hasHandledInitialRefresh = true
        }
    }

    return remember {
        derivedStateOf { hasHandledInitialRefresh.not() && state is LoadState.Loading }
    }
}

@Preview
@Composable
private fun ChatScreenPreview() {
    ChatScreen(
        userViewState = ChatViewState.UserViewState.INITIAL,
        messagesViewState = ChatViewState.MessagesViewState.INITIAL,
        messageInputViewState = ChatViewState.MessageInputViewState.INITIAL,
        showClearChatDialog = false,
        callbacks = ChatScreenCallbacks(
            onMessageInputChanged = {},
            onSendMessage = {},
            onClearChatClick = {},
            onBackClick = {},
            onMoreOptionsClick = {},
            onMoreOptionsDismiss = {},
        ),
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
