package com.example.retrofitroom.mvvm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitroom.data.model.repository.DecoratorRepository

class SomeUserViewModelFactory(
    private val decoratorRepository: DecoratorRepository,
    private val id: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SomeUserViewModel::class.java)) {
            return SomeUserViewModel(decoratorRepository, id) as T
        }
        throw IllegalArgumentException("Unknown View Model")
    }
}

