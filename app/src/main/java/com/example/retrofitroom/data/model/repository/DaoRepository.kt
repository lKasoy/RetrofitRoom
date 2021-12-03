package com.example.retrofitroom.data.model.repository

import com.example.retrofitroom.data.model.dao.UserDao
import com.example.retrofitroom.data.model.entity.UsersTable

class DaoRepository (private val dao: UserDao) {

    suspend fun deleteAll() = dao.deleteAll()

    suspend fun add(users: List<UsersTable>) = dao.add(users)

    suspend fun getUserById(id: String) = dao.getById(id)

    suspend fun getUsers() = dao.getAllUsers()
}


