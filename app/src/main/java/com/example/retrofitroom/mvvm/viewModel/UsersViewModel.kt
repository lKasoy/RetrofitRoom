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

    private val _loadingState = MutableLiveData<Event<List<UsersTable>>>()
    val loadingState: LiveData<Event<List<UsersTable>>> = _loadingState
    private var isFirstResponse: Boolean = true

    init {
        getUsers()
    }

    fun getUsers() {
        _loadingState.postValue(Event.loading())
        this.viewModelScope.launch(Dispatchers.IO) {
            try {
                val currentValue = _loadingState.value
                val currentList = if (currentValue?.status == Status.SUCCESS) {
                    currentValue.data ?: listOf()
                } else listOf()
                val userList = repository.getUsersFromApi()
                val userTable = toDatabase(userList)
                if (isFirstResponse) {
                    repository.deleteAll()
                    isFirstResponse = false
                }
                repository.add(userTable)
                _loadingState.postValue(Event.success(currentList + userTable))
            } catch (e: Exception) {
                e.printStackTrace()
                _loadingState.postValue(Event.error(repository.getUsersFromDatabase()))
            }
        }
    }
}



