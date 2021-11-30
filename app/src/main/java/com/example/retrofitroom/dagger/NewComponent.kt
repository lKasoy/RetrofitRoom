package com.example.retrofitroom.dagger

import com.example.retrofitroom.view.SomeUserFragment
import com.example.retrofitroom.view.UsersFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        RoomModule::class,
        ApiModule::class,
    ]
)

interface NewComponent {
    fun inject(fragment: SomeUserFragment)
    fun inject(fragment: UsersFragment)
}