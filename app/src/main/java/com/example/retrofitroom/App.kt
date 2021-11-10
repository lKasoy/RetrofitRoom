package com.example.retrofitroom

import android.app.Application
import com.example.retrofitroom.di.DI

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        DI.init(this)
    }
}