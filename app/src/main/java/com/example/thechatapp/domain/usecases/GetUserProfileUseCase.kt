package com.example.thechatapp.domain.usecases

import com.example.thechatapp.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface GetUserProfileUseCase {
    operator fun invoke(userId: String): Flow<UserProfile>
}

internal class GetUserProfileUseCaseImpl : GetUserProfileUseCase {

    // Mock implementation for demonstration purposes. In a real application, this would fetch data from a repository or API.
    override fun invoke(userId: String): Flow<UserProfile> {
        return flow {
            emit(
                UserProfile(
                    id = userId,
                    name = "Bot user",
                    imageUrl = "https://media.istockphoto.com/id/1533529011/photo/beauty-shot-of-beautiful-black-woman-in-monochromatic-pink-stock-photo-copy-space.jpg",
                )
            )
        }
    }
}
