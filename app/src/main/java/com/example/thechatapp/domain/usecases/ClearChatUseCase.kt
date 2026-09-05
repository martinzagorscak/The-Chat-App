package com.example.thechatapp.domain.usecases

import com.example.thechatapp.data.repository.ChatRepository

interface ClearChatUseCase {
    suspend operator fun invoke(userId: String)
}

internal class ClearChatUseCaseImpl(
    private val repository: ChatRepository,
) : ClearChatUseCase {

    override suspend fun invoke(userId: String) = repository.clearChat(userId = userId)
}
