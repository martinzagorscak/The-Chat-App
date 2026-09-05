package com.example.thechatapp.data.di

import com.example.thechatapp.data.ChatRepository
import com.example.thechatapp.data.ChatRepositoryImpl
import org.koin.dsl.module

val dataModule = module {
    single<ChatRepository> { ChatRepositoryImpl() }
}
