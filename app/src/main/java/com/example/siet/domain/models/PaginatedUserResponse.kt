package com.example.siet.domain.models

data class PaginatedUserResponse(
    val page: Int,
    val perPage: Int,
    val total: Int,
    val totalPages: Int,
    val data: List<UserOfList>
)

