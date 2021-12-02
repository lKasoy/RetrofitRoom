package com.example.retrofitroom.hilt

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.retrofitroom.constants.Constants
import com.example.retrofitroom.data.model.AppDatabase
import com.example.retrofitroom.data.model.dao.UserDao
import com.example.retrofitroom.data.model.network.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideApplication(app: Application): Context = app
}

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {
    @Provides
    @Singleton
    fun provideApplicationDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "users_database")
            .build()

    @Provides
    @Singleton
    fun provideDao(db: AppDatabase): UserDao = db.getUserDao
}

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Provides
    fun provideApiModule(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    fun provideClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }
}
