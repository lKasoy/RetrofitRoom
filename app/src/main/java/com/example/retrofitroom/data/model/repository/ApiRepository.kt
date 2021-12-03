package com.example.retrofitroom.data.model.repository

import com.example.retrofitroom.data.model.network.UserApi

class ApiRepository (private val userApi: UserApi) {

    suspend fun getUsersFromApi() = userApi.getUsersAsync(15)
}