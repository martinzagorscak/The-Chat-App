package com.example.thechatapp.data.di

import androidx.room.Room
import androidx.sqlite.driver.AndroidSQLiteDriver
import com.example.thechatapp.data.persistance.ChatDatabase
import com.example.thechatapp.data.persistance.MessageDao
import com.example.thechatapp.data.repository.ChatRepository
import com.example.thechatapp.data.repository.ChatRepositoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

private const val APP_DB_NAME = "the-chat-app-db"

val dataModule = module {
    single<ChatRepository> { ChatRepositoryImpl(messageDao = get()) }
    single<MessageDao> {
        Room.databaseBuilder<ChatDatabase>(context = androidApplication(), name = APP_DB_NAME)
            .setDriver(AndroidSQLiteDriver())
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build().messageDao()
    }
}
