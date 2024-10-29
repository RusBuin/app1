package com.example.siet.domain.models

data class UserOfList(
    val id: Int,
    val email: String,
    val first_name: String,
    val last_name: String,
    val avatar: String

) {
    fun getFullName(): String {
        return "${first_name} ${last_name}"
    }
}

