package com.example.retrofitroom.mvvm.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofitroom.data.model.entity.UsersTable
import com.example.retrofitroom.data.model.repository.DaoRepository

class SomeUserViewModel(
    private val repository: DaoRepository,
    private val id: String
) : ViewModel() {

    private var _selectedUser: MutableLiveData<UsersTable> = MutableLiveData()
    val selectedUser: LiveData<UsersTable> = _selectedUser

    suspend fun getSelectedUser() {
        _selectedUser.value = repository.getUserById(id)
    }
}
