package com.example.retrofitroom.dagger

import android.app.Application
import android.content.Context
import androidx.room.Dao
import androidx.room.Room
import com.example.retrofitroom.App
import com.example.retrofitroom.MainActivity
import com.example.retrofitroom.constants.Constants
import com.example.retrofitroom.data.model.AppDatabase
import com.example.retrofitroom.data.model.dao.UserDao
import com.example.retrofitroom.data.model.network.UserApi
import com.example.retrofitroom.data.model.repository.ApiRepository
import com.example.retrofitroom.data.model.repository.DaoRepository
import com.example.retrofitroom.data.model.repository.DecoratorRepository
import dagger.Component
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DataModule::class,
        ApiModule::class,
        DecoratorRepositoryModule::class
    ]
)
interface AppComponent {
    val decoratorRepository: DecoratorRepository
}

@Module
class AppModule(private val app: Application) {
    @Provides
    @Singleton
    fun provideApplication(): Application = app
}

@Module
class DataModule {
    @Provides
    @Singleton
    fun provideApplicationDatabase(app: Application): AppDatabase =
        Room.databaseBuilder(app, AppDatabase::class.java, "users_database")
            .build()

    @Provides
    @Singleton
    fun provideDao(db: AppDatabase): UserDao = db.getUserDao
}

@Module
class DecoratorRepositoryModule {
    @Provides
    fun provideDecoratorRepository(
        apiRepository: ApiRepository,
        daoRepository: DaoRepository
    ): DecoratorRepository {
        return DecoratorRepository(apiRepository = apiRepository, daoRepository = daoRepository)
    }
}

@Module
class ApiModule {
    @Provides
    fun provideApiModule(): ApiRepository {
        val loggingInterceptor by lazy {
            run {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.apply {
                    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                }
            }
        }
        val client: OkHttpClient by lazy {
            OkHttpClient
                .Builder()
                .addInterceptor(loggingInterceptor)
                .build()
        }
        val userApi: UserApi by lazy {
            Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(UserApi::class.java)
        }
        val apiRepository: ApiRepository by lazy {
            ApiRepository(
                userApi
            )
        }
        return apiRepository
    }
}