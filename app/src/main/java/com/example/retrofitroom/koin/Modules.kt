package com.example.retrofitroom.koin

import android.app.Application
import androidx.room.Room
import com.example.retrofitroom.constants.Constants.BASE_URL
import com.example.retrofitroom.data.model.AppDatabase
import com.example.retrofitroom.data.model.dao.UserDao
import com.example.retrofitroom.data.model.network.UserApi
import com.example.retrofitroom.data.model.repository.ApiRepository
import com.example.retrofitroom.data.model.repository.DaoRepository
import com.example.retrofitroom.data.model.repository.DecoratorRepository
import com.example.retrofitroom.mvvm.viewModel.SomeUserViewModel
import com.example.retrofitroom.mvvm.viewModel.UsersViewModel
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {
    viewModel {
        UsersViewModel(get())
    }
    viewModel { parameters ->
        SomeUserViewModel(get(), parameters[0])
    }
}

val netModule = module {

    fun provideHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        return okHttpClientBuilder.build()
    }

    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }

    fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(factory))
            .client(client)
            .build()
    }

    fun provideApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    factory { provideHttpClient() }
    factory { provideGson() }
    factory { provideRetrofit(get(), get()) }
    factory { provideApi(get()) }
}

val dataBaseModule = module {

    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "users")
            .build()
    }

    fun provideDao(database: AppDatabase): UserDao {
        return database.getUserDao
    }

    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
}

val repositoryModule = module {

    fun provideApiRepository(userApi: UserApi): ApiRepository {
        return ApiRepository(userApi)
    }

    fun provideDaoRepository(dao: UserDao): DaoRepository {
        return DaoRepository(dao)
    }

    fun provideDecoratorRepository(
        apiRepository: ApiRepository,
        daoRepository: DaoRepository
    ): DecoratorRepository {
        return DecoratorRepository(apiRepository, daoRepository)
    }

    factory { provideApiRepository(get()) }
    factory { provideDaoRepository(get()) }
    single { provideDecoratorRepository(get(), get()) }
}