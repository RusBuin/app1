package com.example.siet.domain.repository

import androidx.paging.PagingData
import com.example.siet.domain.models.PaginatedUserResponse
import com.example.siet.domain.models.RegistrationRequest
import com.example.siet.domain.models.RegistrationResponse
import com.example.siet.domain.models.UserOfList
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun saveDataUser(saveParam: RegistrationRequest):Boolean
    fun getName(): RegistrationRequest
    suspend fun registerUserViaApi(request:RegistrationRequest):Result<RegistrationResponse>
    fun  getUsers(): Flow<PagingData<UserOfList>>
    suspend fun fetchUsers(page: Int, perPage: Int): Result<PaginatedUserResponse>
}
