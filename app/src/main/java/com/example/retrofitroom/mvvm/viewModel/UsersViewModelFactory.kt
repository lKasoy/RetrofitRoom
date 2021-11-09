package com.example.retrofitroom.mvvm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitroom.data.model.repository.UserRepository

class UsersViewModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsersViewModel::class.java)) {
            return UsersViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}

class SomeUserViewModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SomeUserViewModel::class.java)) {
            return SomeUserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model")
    }
}

