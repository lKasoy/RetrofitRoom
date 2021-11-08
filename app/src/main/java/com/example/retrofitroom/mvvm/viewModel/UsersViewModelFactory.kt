package com.example.retrofitroom.mvvm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitroom.data.model.network.ApiService
import com.example.retrofitroom.data.model.repository.UserRepository

class UsersViewModelFactory (
    private val repository: UserRepository
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(BaseViewModel::class.java)){
                return BaseViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown View Model class")
        }
}
