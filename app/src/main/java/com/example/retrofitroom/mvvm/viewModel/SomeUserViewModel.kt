package com.example.retrofitroom.mvvm.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofitroom.data.model.entity.UsersTable
import com.example.retrofitroom.data.model.repository.UserRepository

class SomeUserViewModel(
    private val repository: UserRepository,
    private val id: String
) : ViewModel() {

    private var _selectedUser: MutableLiveData<UsersTable> = MutableLiveData()
    val selectedUser: LiveData<UsersTable> = _selectedUser

    suspend fun getSelectedUser() {
        _selectedUser.value = repository.getUserById(id)
    }
}
