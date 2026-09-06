package com.example.thechatapp.ui.screens.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.thechatapp.ui.screens.chat.components.AlignInTheMiddle
import com.example.thechatapp.ui.screens.chat.components.ChatFooter
import com.example.thechatapp.ui.screens.chat.components.ChatTopBar
import com.example.thechatapp.ui.screens.chat.components.ClearChatDialog
import com.example.thechatapp.ui.screens.chat.components.EmptyChatState
import com.example.thechatapp.ui.screens.chat.components.LoadingIndicator
import com.example.thechatapp.ui.screens.chat.components.MessagesContent

@Composable
fun ChatScreen(
    userViewState: ChatViewState.UserViewState,
    messagesViewState: ChatViewState.MessagesViewState,
    messageInputViewState: ChatViewState.MessageInputViewState,
    showClearChatDialog: Boolean,
    callbacks: ChatScreenCallbacks,
    modifier: Modifier = Modifier,
) {
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
                isLoading -> AlignInTheMiddle { LoadingIndicator() }

                isEmpty -> AlignInTheMiddle { EmptyChatState() }

                else -> {
                    MessagesContent(
                        lazyPagingMessages = lazyPagingMessages,
                        messagesViewState = messagesViewState,
                        modifier = Modifier.fillMaxSize()
                    )
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
