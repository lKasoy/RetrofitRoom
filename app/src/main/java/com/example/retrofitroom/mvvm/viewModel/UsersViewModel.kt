package com.example.retrofitroom.mvvm.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitroom.data.model.entity.UsersTable
import com.example.retrofitroom.data.model.entity.UsersTable.Companion.getUsersApiToTable
import com.example.retrofitroom.data.model.repository.UserRepository
import com.example.retrofitroom.services.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UsersViewModel(private val repository: UserRepository) : ViewModel() {

    private val _simpleLiveData = MutableLiveData<Event<List<UsersTable>>>()
    val simpleLiveData: LiveData<Event<List<UsersTable>>> = _simpleLiveData

    fun getUsers() {

        if (_simpleLiveData.value == null) {
            _simpleLiveData.postValue(Event.loading())
            this.viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = repository.getUsersFromApi()
                    val userListApi = response.body()
                    val usersTable = getUsersApiToTable(userListApi)
                    _simpleLiveData.postValue(Event.success(usersTable))
                    Log.d("MyApp", "success")
                    repository.add(usersTable)
                    Log.d("MyApp", "add success")

                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.d("MyApp", "$e")
                    Log.d("MyApp", "no response")
                    _simpleLiveData.postValue(Event.loadFromDb(repository.getUsersFromDatabase()))
                }
            }
        }
    }
}



