package com.example.retrofitroom.data.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.retrofitroom.data.model.dao.UserDao
import com.example.retrofitroom.data.model.entity.Users
import com.example.retrofitroom.data.model.entity.UsersTable

@Database(entities = [UsersTable::class],version = 2,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val getUserDao : UserDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "users_database"
                    ).allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return instance
            }
        }
    }
}


