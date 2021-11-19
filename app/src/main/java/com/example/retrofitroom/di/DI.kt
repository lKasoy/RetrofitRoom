package com.example.retrofitroom.di

import android.content.Context
import androidx.room.Room
import com.example.retrofitroom.constants.Constants
import com.example.retrofitroom.data.model.AppDatabase
import com.example.retrofitroom.data.model.dao.UserDao
import com.example.retrofitroom.data.model.network.ApiService
import com.example.retrofitroom.data.model.repository.ApiRepository
import com.example.retrofitroom.data.model.repository.DaoRepository
import com.example.retrofitroom.data.model.repository.DecoratorRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DI {

    private lateinit var contextProvider: () -> Context

    fun init(context: Context) {
        contextProvider = { context }
    }

    private val database: AppDatabase by lazy {
        Room.databaseBuilder(contextProvider(), AppDatabase::class.java, "users_database")
            .build()
    }

    private val dao: UserDao by lazy {
        database.getUserDao
    }

    private val loggingInterceptor by lazy {
        run {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
        }
    }

    private val client: OkHttpClient by lazy {
        OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    private val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiService::class.java)
    }
    private val apiRepository: ApiRepository by lazy {
        ApiRepository(
            apiService = apiService
        )
    }

    val daoRepository: DaoRepository by lazy {
        DaoRepository(
            dao = dao,
        )
    }

    val decoratorRepository: DecoratorRepository by lazy {
        DecoratorRepository(
            apiRepository = apiRepository,
            daoRepository = daoRepository
        )
    }
}