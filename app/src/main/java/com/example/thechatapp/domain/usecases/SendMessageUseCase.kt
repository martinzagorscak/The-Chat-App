package com.example.thechatapp.domain.usecases

import com.example.thechatapp.data.ChatRepository

interface SendMessageUseCase {
    suspend operator fun invoke(message: String)
}

internal class SendMessageUseCaseImpl(
    private val repository: ChatRepository,
) : SendMessageUseCase {

    override suspend fun invoke(message: String) = repository.sendMessage(message)
}
