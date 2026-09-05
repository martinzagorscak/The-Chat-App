package com.example.thechatapp.ui.screens.chat.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.thechatapp.ui.theme.Typography
import com.example.thechatapp.ui.theme.tertiaryColor

private val timeStampFontSize = 18.sp

@Composable
fun ChatTimeStampDividerItem(
    dayOfWeek: String,
    time: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxWidth(),
    ) {

        val formattedTime = buildAnnotatedString {
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append("$dayOfWeek ")
            }
            append(time)
        }
        Text(
            text = formattedTime,
            style = Typography.bodyLarge.copy(fontSize = timeStampFontSize),
            color = tertiaryColor,
        )
    }
}

@Preview
@Composable
private fun ChatTimeStampDividerItemPreview() {
    ChatTimeStampDividerItem(
        dayOfWeek = "Thursday",
        time = "11:59"
    )
}
