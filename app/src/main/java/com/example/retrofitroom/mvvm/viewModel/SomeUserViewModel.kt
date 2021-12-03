package com.example.retrofitroom.mvvm.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitroom.data.model.entity.UsersTable
import com.example.retrofitroom.data.model.repository.DecoratorRepository
import kotlinx.coroutines.launch

class SomeUserViewModel(
    private val decoratorRepository: DecoratorRepository,
    private val id: String
) : ViewModel() {

    private var _selectedUser: MutableLiveData<UsersTable> = MutableLiveData()
    val selectedUser: LiveData<UsersTable> = _selectedUser

    fun getSelectedUser() {
        viewModelScope.launch {
            _selectedUser.value = decoratorRepository.getUserById(id)
        }
    }
}
