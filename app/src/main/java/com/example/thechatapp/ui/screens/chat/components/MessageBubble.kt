package com.example.thechatapp.ui.screens.chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thechatapp.R
import com.example.thechatapp.ui.theme.Typography
import com.example.thechatapp.ui.theme.messageReadColor
import com.example.thechatapp.ui.theme.padding100
import com.example.thechatapp.ui.theme.padding200
import com.example.thechatapp.ui.theme.padding300
import com.example.thechatapp.ui.theme.primaryColor
import com.example.thechatapp.ui.theme.tertiaryColor

private val messageReadIconSize = 16.dp
private val messageFontSize = 20.sp
private const val ROUNDED_CORNER_PERCENT = 20

@Composable
fun MessageBubble(
    text: String,
    isCurrentUserMessage: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(
                RoundedCornerShape(
                    topStartPercent = ROUNDED_CORNER_PERCENT,
                    topEndPercent = ROUNDED_CORNER_PERCENT,
                    bottomStartPercent = if (isCurrentUserMessage) ROUNDED_CORNER_PERCENT else 0,
                    bottomEndPercent = if (isCurrentUserMessage) 0 else ROUNDED_CORNER_PERCENT
                )
            )
            .background(if (isCurrentUserMessage) primaryColor else tertiaryColor)
    ) {
        Text(
            text = text,
            style = Typography.bodyLarge.copy(fontSize = messageFontSize),
            modifier = Modifier.padding(padding300)
        )

        // Since this app is fully offline, read message marker is here for decoration
        if (isCurrentUserMessage) {
            Icon(
                painter = painterResource(R.drawable.ic_message_read),
                contentDescription = null,
                tint = messageReadColor,
                modifier = Modifier
                    .padding(padding100)
                    .size(messageReadIconSize)
                    .align(Alignment.BottomEnd)
            )
        }
    }
}

@Preview
@Composable
private fun MessageBubblePreview() {
    Column(verticalArrangement = Arrangement.spacedBy(padding200)) {
        MessageBubble(
            text = "Hello!",
            isCurrentUserMessage = true,
        )
        MessageBubble(
            text = "Hi there!",
            isCurrentUserMessage = false,
        )
    }
}
