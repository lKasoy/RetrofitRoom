package com.example.retrofitroom.mvvm.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitroom.data.model.entity.UsersTable
import com.example.retrofitroom.data.model.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SomeUserViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private var _selectedUser: MutableLiveData<UsersTable> = MutableLiveData()
    val selectedUser: LiveData<UsersTable> = _selectedUser

    fun getSelectedUser(id: String) {
        _selectedUser.value = repository.getUserById(id)
    }
}
