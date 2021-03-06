package com.example.retrofitroom.mvvm.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitroom.data.model.entity.UsersTable
import com.example.retrofitroom.data.model.repository.DecoratorRepository
import com.example.retrofitroom.services.LoadingState
import kotlinx.coroutines.launch

class UsersViewModel(private val decoratorRepository: DecoratorRepository) : ViewModel() {

    private val _loadingState = MutableLiveData<LoadingState>()
    private val _data = MutableLiveData<List<UsersTable>>()
    val data: LiveData<List<UsersTable>> = _data

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            try {
                _loadingState.value = LoadingState.LOADING
                val users = decoratorRepository.getUsers()
                val currentUsers = _data.value ?: listOf()
                _data.value = currentUsers + users
                _loadingState.value = LoadingState.LOADED
            } catch (e: Exception) {
                _loadingState.value = LoadingState.error(e.message)
            }
        }
    }
}


