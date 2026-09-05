package com.example.thechatapp.ui.screens.di

import com.example.thechatapp.ui.screens.chat.ChatViewModel
import com.example.thechatapp.ui.screens.chat.ChatViewModelImpl
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<ChatViewModel> {
        ChatViewModelImpl(
            gerUserProfileUseCase = get(),
            getChatMessagesUseCase = get(),
            sendMessageUseCase = get(),
            clearChatUseCase = get(),
        )
    }
}
