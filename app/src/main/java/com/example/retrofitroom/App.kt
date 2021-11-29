package com.example.retrofitroom

import android.app.Application
import com.example.retrofitroom.dagger.AppModule
import com.example.retrofitroom.dagger.DaggerAppComponent
import com.example.retrofitroom.data.model.repository.DecoratorRepository

class App : Application() {
    companion object {
        lateinit var decoratorRepository: DecoratorRepository
    }

    override fun onCreate() {
        super.onCreate()
        decoratorRepository = getDecoratorRepository(this)
    }

    private fun getDecoratorRepository(app: App): DecoratorRepository {
        return DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .build().decoratorRepository
    }
}