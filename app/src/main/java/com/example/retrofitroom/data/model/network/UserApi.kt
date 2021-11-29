package com.example.retrofitroom.data.model.network

import com.example.retrofitroom.data.model.entity.Users
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {

    @GET("?")
    suspend fun getUsersAsync(@Query("results") quantity: Int) : Users
}
