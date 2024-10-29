package com.example.siet.data.repository

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
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userStorage: UserStorage,
    private val apiService: ApiService
) : UserRepository {

    override fun saveDataUser(saveParam: RegistrationRequest): Boolean {
        val user = User(email = saveParam.email, password = saveParam.password)
        return userStorage.save(user)
    }

    override suspend fun registerUserViaApi(request: RegistrationRequest): Result<RegistrationResponse> {
        return try {
            val response = apiService.registerUser(request)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getName(): RegistrationRequest {
        val user = userStorage.get()
        return RegistrationRequest(email = user.email, password = user.password)
    }

    override fun getUsers(): Flow<PagingData<UserOfList>> {
        return Pager(
            config = PagingConfig(pageSize = 20), // Установите размер страницы
            pagingSourceFactory = { UserPagingSource(apiService) }
        ).flow
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
