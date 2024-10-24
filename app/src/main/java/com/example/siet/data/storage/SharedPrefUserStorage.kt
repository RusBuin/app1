package com.example.siet.data.storage

import android.content.Context
import android.content.SharedPreferences


import com.example.siet.domain.models.RegistrationRequest

private const val SHARED_PREFS_NAME = "shared_prefs_name"
private const val KEY_EMAIL = "email"
private const val KEY_PASSWORD = "password"

class SharedPrefUserStorage (context: Context) : UserStorage {
    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    override fun save(user:User): Boolean {
        sharedPreferences.edit().putString(KEY_EMAIL, user.email).apply()
        sharedPreferences.edit().putString(KEY_PASSWORD, user.password).apply()
        return true
    }

    override fun get(): User {
        val email = sharedPreferences.getString(KEY_EMAIL, "") ?: ""
        val password = sharedPreferences.getString(KEY_PASSWORD, "") ?: ""
        return User(email = email, password = password)
    }

}