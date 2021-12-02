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
        UserViewModelModule::class
    ]
)

interface NewComponent {
    fun inject(fragment: UsersFragment)
    fun inject(fragment: SomeUserFragment)
}