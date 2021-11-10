package com.example.retrofitroom.mvvm.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitroom.data.model.entity.UsersTable
import com.example.retrofitroom.data.model.entity.UsersTable.Companion.toDatabase
import com.example.retrofitroom.data.model.repository.UserRepository
import com.example.retrofitroom.services.Event
import com.example.retrofitroom.services.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UsersViewModel(private val repository: UserRepository) : ViewModel() {

    private val _simpleLiveData = MutableLiveData<Event<List<UsersTable>>>()
    val simpleLiveData: LiveData<Event<List<UsersTable>>> = _simpleLiveData

    fun getUsers() {

        val currentValue = _simpleLiveData.value
        val currentList = if (currentValue?.status == Status.SUCCESS) {
            currentValue.data ?: listOf()
        } else {
            listOf()
        }

        _simpleLiveData.postValue(Event.loading())
        this.viewModelScope.launch(Dispatchers.IO) {
            try {
                val userList = repository.getUsersFromApi()
                val usersTable = currentList + toDatabase(userList)
                _simpleLiveData.postValue(Event.success(usersTable))
                repository.add(usersTable)
            } catch (e: Exception) {
                e.printStackTrace()
                _simpleLiveData.postValue(Event.loadFromDb(repository.getUsersFromDatabase()))
            }
        }
    }
}



