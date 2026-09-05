package com.example.thechatapp.domain.di

import com.example.thechatapp.domain.usecases.ClearChatUseCase
import com.example.thechatapp.domain.usecases.ClearChatUseCaseImpl
import com.example.thechatapp.domain.usecases.GetChatMessagesUseCase
import com.example.thechatapp.domain.usecases.GetChatMessagesUseCaseImpl
import com.example.thechatapp.domain.usecases.GetUserProfileUseCase
import com.example.thechatapp.domain.usecases.GetUserProfileUseCaseImpl
import com.example.thechatapp.domain.usecases.SendMessageUseCase
import com.example.thechatapp.domain.usecases.SendMessageUseCaseImpl
import org.koin.dsl.module

val domainModule = module {
    single<GetChatMessagesUseCase> { GetChatMessagesUseCaseImpl(repository = get()) }
    single<SendMessageUseCase> { SendMessageUseCaseImpl(repository = get()) }
    single<GetUserProfileUseCase> { GetUserProfileUseCaseImpl() }
    single<ClearChatUseCase> { ClearChatUseCaseImpl(repository = get()) }
}
