package com.example.retrofitroom.mvvm.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitroom.data.model.entity.UsersTable
import com.example.retrofitroom.data.model.repository.DecoratorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val decoratorRepository: DecoratorRepository
) : ViewModel() {

    private val _data = MutableLiveData<List<UsersTable>>()
    val data: LiveData<List<UsersTable>> = _data

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            try {
                val users = decoratorRepository.getUsers()
                val currentUsers = _data.value ?: listOf()
                _data.value = currentUsers + users
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}


