package com.example.thechatapp

import android.app.Application
import com.example.thechatapp.data.di.dataModule
import com.example.thechatapp.domain.di.domainModule
import com.example.thechatapp.ui.screens.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TheChatApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@TheChatApp)
            modules(dataModule, domainModule, viewModelModule)
        }
    }
}
