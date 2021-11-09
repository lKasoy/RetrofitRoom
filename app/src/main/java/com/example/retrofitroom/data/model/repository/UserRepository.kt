package com.example.retrofitroom.data.model.repository

import com.example.retrofitroom.data.model.dao.UserDao
import com.example.retrofitroom.data.model.entity.Users
import com.example.retrofitroom.data.model.entity.UsersTable
import com.example.retrofitroom.data.model.network.ApiService

class UserRepository(private val dao: UserDao, private val apiService: ApiService) {

    suspend fun getUsersFromApi() = apiService.getUsersAsync(10)

    fun getUsersFromDatabase() = dao.getAllUsers()

    fun getUserById(id: String) = dao.getById(id)

    fun add(users: List<UsersTable>) {
        dao.deleteAll()
        dao.add(users)
    }
}
