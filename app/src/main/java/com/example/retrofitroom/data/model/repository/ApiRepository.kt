package com.example.retrofitroom.data.model.repository

import com.example.retrofitroom.data.model.network.UserApi
import javax.inject.Inject

class ApiRepository @Inject constructor(private val userApi: UserApi) {

    suspend fun getUsersFromApi() = userApi.getUsersAsync(15)
}