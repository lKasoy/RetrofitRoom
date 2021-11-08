package com.example.retrofitroom.mvvm.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitroom.data.model.entity.Users
import com.example.retrofitroom.data.model.entity.UsersTable.Companion.getUsersApiToTable
import com.example.retrofitroom.data.model.repository.UserRepository
import com.example.retrofitroom.services.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class BaseViewModel(private val repository: UserRepository) : ViewModel() {

    val simpleLiveData = MutableLiveData<Event<Users>>()

    fun getUsers() {
        getAllUsers(simpleLiveData) {
            repository.getUsersFromApi()
        }
    }

    private fun <T> getAllUsers(
        liveData: MutableLiveData<Event<T>>,
        request: suspend () -> Response<Users>
    ) {

        if (liveData.value == null) {
            liveData.postValue(Event.loading())
            this.viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = request.invoke()
                        val userListApi = response.body()
                        val usersTable = getUsersApiToTable(userListApi)
                        liveData.postValue(Event.success(usersTable))
                        Log.d("MyApp", "success")
                        repository.add(usersTable)
                        Log.d("MyApp", "add success")

                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.d("MyApp", "$e")
                    Log.d("MyApp", "no response")
                    liveData.postValue(Event.loadFromDb(repository.getUsersFromDatabase()))

                }
            }
        }
    }
}



