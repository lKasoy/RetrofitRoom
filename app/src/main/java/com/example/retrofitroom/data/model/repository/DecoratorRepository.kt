package com.example.retrofitroom.data.model.repository

import com.example.retrofitroom.data.model.entity.UsersTable
import com.example.retrofitroom.data.model.entity.UsersTable.Companion.toDatabase
import javax.inject.Inject

class DecoratorRepository (
    private val apiRepository: ApiRepository,
    private val daoRepository: DaoRepository
) {
    private var isFirstResponse: Boolean = true

    suspend fun getUsers(): List<UsersTable> {
        return try {
            val users = toDatabase(apiRepository.getUsersFromApi())
            if (isFirstResponse) {
                daoRepository.deleteAll()
                isFirstResponse = false
            }
            daoRepository.add(users)
            users
        } catch (e: Exception) {
            daoRepository.getUsers()
        }
    }

    suspend fun getUserById(id: String) = daoRepository.getUserById(id)
}