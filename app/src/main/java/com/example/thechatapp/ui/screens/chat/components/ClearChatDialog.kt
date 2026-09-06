package com.example.thechatapp.ui.screens.chat.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import com.example.thechatapp.R
import com.example.thechatapp.ui.theme.padding200
import com.example.thechatapp.ui.theme.warningColor

@Composable
fun ClearChatDialog(
    onConfirmButtonClick: () -> Unit,
    onDismissButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        title = { Text(text = stringResource(R.string.clear_chat_dialog_title)) },
        text = { Text(text = stringResource(R.string.clear_chat_dialog_message)) },
        confirmButton = {
            Text(
                text = stringResource(R.string.clear_chat_dialog_positive_button),
                color = warningColor,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(onClick = onConfirmButtonClick)
                    .padding(padding200)
            )
        },
        dismissButton = {
            Text(
                text = stringResource(R.string.clear_chat_dialog_negative_button),
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(onClick = onDismissButtonClick)
                    .padding(padding200)
            )
        },
        onDismissRequest = onDismissButtonClick,
        modifier = modifier,
    )
}
