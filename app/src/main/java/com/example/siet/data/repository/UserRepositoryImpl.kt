package com.example.siet.data.repository

import com.example.siet.data.network.ApiService
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
//    override suspend fun getUsers(): Flow<List<UserOfList>> {
//        return flow {
//            try {
//                val response = apiService.getUsers()
//
//                if (response.isSuccessful) {
//                    val users = response.body()?.data ?: emptyList()
//                    emit(users)
//                } else {
//                    emit(emptyList()) s databasy
//                }
//            } catch (e: Exception) {
//                emit(emptyList())
//            }
//        }
//    }

//    override suspend fun fetchUsers(page: Int, perPage: Int): Result<PaginatedUserResponse> {
//        return try {
//            val response = apiService.getUsers(page, perPage)
//            if (response.isSuccessful) {
//                Result.success(response.body()!!)
//            } else {
//                Result.failure(Exception("Error: ${response.code()}"))
//            }
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }

}
