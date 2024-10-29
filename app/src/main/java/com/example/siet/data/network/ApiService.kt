package com.example.siet.data.network

import com.example.siet.domain.models.PaginatedUserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import com.example.siet.domain.models.RegistrationRequest
import com.example.siet.domain.models.RegistrationResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @POST("register")
    suspend fun registerUser(@Body request: RegistrationRequest): Response<RegistrationResponse>

    @GET("users")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<PaginatedUserResponse>
}
