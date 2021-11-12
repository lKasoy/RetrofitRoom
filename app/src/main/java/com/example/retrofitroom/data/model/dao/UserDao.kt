package com.example.retrofitroom.data.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.retrofitroom.data.model.entity.UsersTable

@Dao
interface UserDao {

    @Query("SELECT * FROM table_users")
    fun getAllUsers(): List<UsersTable>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(result: List<UsersTable>)

    @Query("DELETE FROM table_users")
    fun deleteAll()

    @Query("SELECT * FROM table_users WHERE uuid = :uuid")
    suspend fun getById(uuid: String): UsersTable


}