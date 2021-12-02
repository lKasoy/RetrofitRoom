package com.example.retrofitroom.koin

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppKoin: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AppKoin)
            modules(listOf(viewModelModule, repositoryModule, dataBaseModule, netModule))
        }
    }
}