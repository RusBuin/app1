package com.example.siet.di

import android.content.Context
import com.example.siet.data.network.ApiService
import com.example.siet.data.repository.UserRepositoryImpl
import com.example.siet.data.storage.SharedPrefUserStorage
import com.example.siet.data.storage.UserStorage
import com.example.siet.domain.GetUsersUseCase
import com.example.siet.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)

class DataModule {
    @Provides
    fun provideUserStorage(context: Context): UserStorage {
        return SharedPrefUserStorage(context = context)
    }

    @Provides
    fun provideApiService(): ApiService {
        val BASE_URL = "https://reqres.in/api/"
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService: ApiService = retrofit.create(ApiService::class.java)
        return apiService
    }

    @Provides
    fun provideUserRepository(userStorage: UserStorage, apiService: ApiService): UserRepository {
        return UserRepositoryImpl(
            userStorage = userStorage,
            apiService = apiService
        )
    }
    @Provides
    fun provideGetUsersUseCase(userRepository: UserRepository): GetUsersUseCase {
        return GetUsersUseCase(userRepository)
    }
}