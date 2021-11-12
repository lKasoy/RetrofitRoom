package com.example.retrofitroom.data.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.retrofitroom.data.model.dao.UserDao
import com.example.retrofitroom.data.model.entity.UsersTable

@Database(entities = [UsersTable::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val getUserDao: UserDao

}


