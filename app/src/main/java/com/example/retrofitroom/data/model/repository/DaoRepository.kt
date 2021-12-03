package com.example.retrofitroom.data.model.repository

import com.example.retrofitroom.data.model.dao.UserDao
import com.example.retrofitroom.data.model.entity.UsersTable
import javax.inject.Inject

class DaoRepository @Inject constructor(private val dao: UserDao) {

    suspend fun deleteAll() = dao.deleteAll()

    suspend fun add(users: List<UsersTable>) = dao.add(users)

    suspend fun getUserById(id: String): UsersTable = dao.getById(id)

    suspend fun getUsers(): List<UsersTable> = dao.getAllUsers()
}


