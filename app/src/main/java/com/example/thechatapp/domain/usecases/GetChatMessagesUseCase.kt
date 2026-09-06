package com.example.thechatapp.domain.usecases

import androidx.paging.PagingData
import com.example.thechatapp.data.repository.ChatRepository
import com.example.thechatapp.domain.model.ChatItem
import kotlinx.coroutines.flow.Flow

interface GetChatMessagesUseCase {
    operator fun invoke(userId: String): Flow<PagingData<ChatItem>>
}

internal class GetChatMessagesUseCaseImpl(
    private val repository: ChatRepository,
) : GetChatMessagesUseCase {

    override fun invoke(userId: String): Flow<PagingData<ChatItem>> = repository.chatMessages(userId = userId)
}
