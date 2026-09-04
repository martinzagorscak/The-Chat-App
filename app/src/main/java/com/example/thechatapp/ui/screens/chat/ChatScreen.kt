package com.example.thechatapp.ui.screens.chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.thechatapp.R
import com.example.thechatapp.ui.theme.padding200
import com.example.thechatapp.ui.theme.secondaryColor

@Composable
fun ChatScreen(
    showClearChatDialog: Boolean,
    callbacks: ChatScreenCallbacks,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            // TODO - connect with VM view state
            Column {
                ChatTopBar(
                    userImageUrl = "https://media.istockphoto.com/id/1533529011/photo/beauty-shot-of-beautiful-black-woman-in-monochromatic-pink-stock-photo-copy-space.jpg",
                    userName = "Jane",
                    onBackClick = callbacks.onBackClick,
                    onMoreOptionsClick = callbacks.onMoreOptionsClick,
                )
                HorizontalDivider(
                    color = Color.Transparent,
                    modifier = Modifier.shadow(DividerDefaults.Thickness)
                )
            }
        },
        bottomBar = {
            // TODO - connect with VM view state
            var messageState by remember { mutableStateOf("") }
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
                    message = messageState,
                    onMessageInputChange = { messageState = it },
                    onSendMessageClick = callbacks.onSendMessage
                )
            }
        },
        modifier = modifier.fillMaxSize(),
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {

        }

        if (showClearChatDialog) {
            AlertDialog(
                title = { Text(text = stringResource(R.string.clear_chat_dialog_title)) },
                text = { Text(text = stringResource(R.string.clear_chat_dialog_message)) },
                confirmButton = {
                    Text(
                        text = stringResource(R.string.clear_chat_dialog_positive_button),
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable(onClick = callbacks.onClearChatClick)
                            .padding(padding200)
                    )
                },
                dismissButton = {
                    Text(
                        text = stringResource(R.string.clear_chat_dialog_negative_button),
                        color = secondaryColor,
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
}

@Preview
@Composable
private fun ChatScreenPreview() {
    ChatScreen(
        showClearChatDialog = false,
        callbacks = ChatScreenCallbacks(
            onSendMessage = {},
            onClearChatClick = {},
            onBackClick = {},
            onMoreOptionsClick = {},
            onMoreOptionsDismiss = {},
        )
    )
}

data class ChatScreenCallbacks(
    val onSendMessage: () -> Unit,
    val onClearChatClick: () -> Unit,
    val onBackClick: () -> Unit,
    val onMoreOptionsClick: () -> Unit,
    val onMoreOptionsDismiss: () -> Unit,
)
