package com.example.thechatapp.data.persistance

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.thechatapp.data.persistance.model.Message

@Database(entities = [Message::class], version = 1)
abstract class ChatDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
}
