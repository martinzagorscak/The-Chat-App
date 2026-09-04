package com.example.thechatapp.ui.screens.chat.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thechatapp.R
import com.example.thechatapp.ui.theme.Typography
import com.example.thechatapp.ui.theme.padding400
import com.example.thechatapp.ui.theme.padding800

private val emptyStateIconSize = 256.dp

@Composable
fun EmptyChatState(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(padding800)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_writing),
            contentDescription = null,
            modifier = Modifier.size(emptyStateIconSize)
        )


        Text(
            text = stringResource(R.string.chat_empty_state_title),
            style = Typography.headlineMedium,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(padding400))


        Text(
            text = stringResource(R.string.chat_empty_state_description),
            style = Typography.bodyLarge,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
private fun EmptyChatStatePreview() {
    EmptyChatState()
}
