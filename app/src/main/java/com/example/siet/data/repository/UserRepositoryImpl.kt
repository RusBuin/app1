package com.example.siet.data.repository

import android.content.Context
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.siet.data.network.ApiService
import com.example.siet.data.paging.UserPagingSource
import com.example.siet.data.storage.User
import com.example.siet.data.storage.UserStorage
import com.example.siet.domain.models.PaginatedUserResponse
import com.example.siet.domain.models.RegistrationRequest
import com.example.siet.domain.models.RegistrationResponse
import com.example.siet.domain.models.UserOfList
import com.example.siet.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userStorage: UserStorage,
    private val apiService: ApiService,
    private val context: Context
) : UserRepository {

    override fun saveDataUser(saveParam: RegistrationRequest): Boolean {
        val user = User(email = saveParam.email, password = saveParam.password)
        return userStorage.save(user)
    }

    override suspend fun registerUserViaApi(request: RegistrationRequest): Result<RegistrationResponse> {
        return try {

            val response = apiService.registerUser(request)

            if (response.isSuccessful) {
                Log.d("TAG", "registerUser: Successful registration")


                val userId = response.body()?.id
                val token = response.body()?.token

                if (userId != null && token != null) {

                    val user = User(email = request.email, password = request.password)
                    userStorage.save(user)
                    val sharedPreferences = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                    sharedPreferences.edit().putBoolean("isUserRegistered", true).apply()
                    sharedPreferences.edit().putString("token", token).apply()
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("User ID or token not found in response."))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e("TAG", "registerUser: Error ${response.code()} - $errorBody")
                Result.failure(Exception("Error: $errorBody"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }




    override fun getName(): RegistrationRequest {
        val user = userStorage.get()
        return RegistrationRequest(email = user.email, password = user.password)
    }

    override fun getUsers(): Flow<PagingData<UserOfList>> = flow {
        val initialResponse = apiService.getUsers(page = 1, perPage = 6)
        if (initialResponse.isSuccessful) {
            val perPage = initialResponse.body()?.perPage ?: 6
            val pager = Pager(
                config = PagingConfig(pageSize = perPage),
                pagingSourceFactory = { UserPagingSource(apiService, perPage) }
            )

            emitAll(pager.flow)
        } else {
            emit(PagingData.empty())
        }
    }

    override suspend fun fetchUsers(page: Int, perPage: Int): Result<PaginatedUserResponse> {
        return try {
            val response = apiService.getUsers(page, perPage)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
