package com.example.retrofitroom.data.model.repository

import com.example.retrofitroom.data.model.network.ApiService

class ApiRepository(private val apiService: ApiService) {

    suspend fun getUsersFromApi() = apiService.getUsersAsync(15)
}