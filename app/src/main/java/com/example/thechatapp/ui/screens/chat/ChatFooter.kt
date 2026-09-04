package com.example.thechatapp.ui.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thechatapp.R
import com.example.thechatapp.ui.theme.backgroundColor
import com.example.thechatapp.ui.theme.padding200
import com.example.thechatapp.ui.theme.padding400
import com.example.thechatapp.ui.theme.primaryColor
import com.example.thechatapp.ui.theme.secondaryColor
import com.example.thechatapp.ui.theme.tertiaryColor

private val FooterIconSize = 48.dp
private const val MAX_MESSAGE_LENGTH = 200

@Composable
fun ChatFooter(
    message: String,
    onMessageInputChange: (String) -> Unit,
    onSendMessageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth(),
    ) {
        OutlinedTextField(
            value = message,
            onValueChange = { value ->
                if (value.length < MAX_MESSAGE_LENGTH) {
                    onMessageInputChange(value)
                }
            },
            placeholder = { Text(text = stringResource(R.string.message_input_placeholder)) },
            singleLine = true,
            shape = CircleShape,
            colors = OutlinedTextFieldDefaults.colors().copy(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = primaryColor,
                unfocusedIndicatorColor = tertiaryColor,
                textSelectionColors = TextSelectionColors(
                    handleColor = secondaryColor,
                    backgroundColor = tertiaryColor,
                ),
            ),
            modifier = Modifier
                .weight(1f)
                .padding(padding400),
        )

        IconButton(
            onClick = onSendMessageClick,
            enabled = message.isNotBlank(),
            modifier = Modifier
                .padding(end = padding400)
                .size(FooterIconSize + padding200)
                .clip(CircleShape)
                .alpha(1f.takeIf { message.isNotBlank() } ?: 0.5f)
                .background(color = primaryColor)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_send),
                contentDescription = "Send Message",
                tint = backgroundColor,
                modifier = Modifier.size(FooterIconSize)
            )
        }
    }
}

@Preview
@Composable
private fun ChatFooterPreview() {
    ChatFooter(
        message = "Hello",
        onMessageInputChange = {},
        onSendMessageClick = {}
    )
}
