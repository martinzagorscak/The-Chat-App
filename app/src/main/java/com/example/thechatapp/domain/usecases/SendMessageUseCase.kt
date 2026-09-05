package com.example.thechatapp.domain.usecases

import com.example.thechatapp.data.repository.ChatRepository

interface SendMessageUseCase {
    suspend operator fun invoke(
        userId: String,
        receiverId: String,
        message: String
    )
}

internal class SendMessageUseCaseImpl(
    private val repository: ChatRepository,
) : SendMessageUseCase {

    override suspend fun invoke(
        userId: String,
        receiverId: String,
        message: String
    ) = repository.sendMessage(
        userId = userId,
        receiverId = receiverId,
        message = message,
    )
}
