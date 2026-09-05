package com.example.thechatapp.domain.usecases

import com.example.thechatapp.data.ChatRepository
import com.example.thechatapp.domain.model.ChatItem
import kotlinx.coroutines.flow.Flow

interface GetChatMessagesUseCase {
    operator fun invoke(userId: String): Flow<List<ChatItem>>
}

internal class GetChatMessagesUseCaseImpl(
    private val repository: ChatRepository,
) : GetChatMessagesUseCase {

    override fun invoke(userId: String): Flow<List<ChatItem>> = repository.chatMessages(userId = userId)
}
