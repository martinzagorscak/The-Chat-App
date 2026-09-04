package com.example.thechatapp.ui.theme.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.thechatapp.R
import com.example.thechatapp.ui.theme.Typography
import com.example.thechatapp.ui.theme.padding100
import com.example.thechatapp.ui.theme.padding200
import com.example.thechatapp.ui.theme.secondaryColor
import com.example.thechatapp.ui.theme.tertiaryColor

private val TopBarIconSize = 36.dp
private val TopBarProfileImageSize = 36.dp

@Composable
fun ChatTopBar(
    userImageUrl: String,
    userName: String,
    onBackClick: () -> Unit,
    onMoreOptionsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(padding200),
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.size(TopBarIconSize + padding200)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "Back",
                tint = secondaryColor,
                modifier = Modifier.size(TopBarIconSize)
            )
        }

        Spacer(modifier = Modifier.width(padding200))

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(userImageUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            placeholder = painterResource(R.drawable.user_profile_placeholder),
            fallback = painterResource(R.drawable.user_profile_placeholder),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .size(TopBarProfileImageSize),
        )

        Spacer(modifier = Modifier.width(padding200))

        Text(
            text = userName,
            style = Typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .weight(1f)
                .padding(end = padding100)
        )

        IconButton(
            onClick = onMoreOptionsClick,
            modifier = Modifier.size(TopBarIconSize + padding200)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_more),
                contentDescription = "More Options",
                tint = tertiaryColor,
                modifier = Modifier.size(TopBarIconSize)
            )
        }
    }
}

@Preview
@Composable
private fun ChatTopBarPreview() {
    ChatTopBar(
        userImageUrl = "https://media.istockphoto.com/id/1533529011/photo/beauty-shot-of-beautiful-black-woman-in-monochromatic-pink-stock-photo-copy-space.jpg",
        userName = "Jane",
        onBackClick = {},
        onMoreOptionsClick = {}
    )
}
