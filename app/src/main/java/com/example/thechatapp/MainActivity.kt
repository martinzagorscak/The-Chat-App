package com.example.thechatapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.thechatapp.ui.theme.TheChatAppTheme
import com.example.thechatapp.ui.theme.screens.ChatScreen
import com.example.thechatapp.ui.theme.screens.ChatScreenCallbacks

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheChatAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // TODO - connect with VM methods
                    var showClearChatDialog by remember { mutableStateOf(false) }
                    ChatScreen(
                        showClearChatDialog = showClearChatDialog,
                        callbacks = remember {
                            ChatScreenCallbacks(
                                onSendMessage = {},
                                onClearChatClick = {},
                                onBackClick = {},
                                onMoreOptionsClick = { showClearChatDialog = true },
                                onMoreOptionsDismiss = { showClearChatDialog = false },
                            )
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
