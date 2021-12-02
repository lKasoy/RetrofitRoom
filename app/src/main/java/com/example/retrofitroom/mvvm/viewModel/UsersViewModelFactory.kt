package com.example.retrofitroom.mvvm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitroom.data.model.repository.ApiRepository
import com.example.retrofitroom.data.model.repository.DaoRepository
import com.example.retrofitroom.data.model.repository.DecoratorRepository

//class UsersViewModelFactory(
//    private val decoratorRepository: DecoratorRepository
//) : ViewModelProvider.Factory {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(UsersViewModel::class.java)) {
//            return UsersViewModel(decoratorRepository) as T
//        }
//        throw IllegalArgumentException("Unknown View Model class")
//    }
//}

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

