package com.example.siet.data.storage

import android.content.Context
import com.example.siet.domain.models.RegistrationRequest

interface UserStorage {

    fun save(user: User) : Boolean

    fun get(): User
}