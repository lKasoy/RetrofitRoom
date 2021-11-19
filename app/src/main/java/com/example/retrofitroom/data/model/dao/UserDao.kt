package com.example.retrofitroom.data.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.retrofitroom.data.model.entity.UsersTable

@Dao
interface UserDao {

    @Query("SELECT * FROM table_users")
    suspend fun getAllUsers(): List<UsersTable>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(users: List<UsersTable>)

    @Query("DELETE FROM table_users")
    suspend fun deleteAll()

    @Query("SELECT * FROM table_users WHERE uuid = :uuid")
    suspend fun getById(uuid: String): UsersTable
}