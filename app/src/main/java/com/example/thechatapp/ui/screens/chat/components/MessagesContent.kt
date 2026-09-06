package com.example.thechatapp.ui.screens.chat.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.thechatapp.ui.model.PresentableChatItem
import com.example.thechatapp.ui.screens.chat.ChatViewState
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
fun MessagesContent(
    lazyPagingMessages: LazyPagingItems<PresentableChatItem>,
    messagesViewState: ChatViewState.MessagesViewState,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val lazyColumnState = rememberLazyListState()
    LazyColumn(
        state = lazyColumnState,
        verticalArrangement = Arrangement.spacedBy(defaultMessagePadding),
        contentPadding = PaddingValues(padding400),
        reverseLayout = true,
        modifier = modifier,
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
