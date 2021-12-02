package com.example.retrofitroom.dagger

import android.app.Application

class App : Application() {

    val component: NewComponent by lazy {
        DaggerNewComponent.builder()
            .appModule(AppModule(applicationContext))
            .build()
    }
}