package com.example.siet.data.storage

import android.content.Context
import android.content.SharedPreferences

private const val SHARED_PREFS_NAME = "shared_prefs_name"
private const val KEY_EMAIL = "email"
private const val KEY_PASSWORD = "password"

class SharedPrefUserStorage(context: Context) : UserStorage {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    override fun save(user: User): Boolean {
        sharedPreferences.edit().apply {
            putString(KEY_EMAIL, user.email)
            putString(KEY_PASSWORD, user.password)
        }.apply()
        return true
    }

    override fun get(): User {
        val email = sharedPreferences.getString(KEY_EMAIL, null)
        val password = sharedPreferences.getString(KEY_PASSWORD, null)

        return if (email != null && password != null) {
            User(email = email, password = password)
        } else {
            User(email = "", password = "")
        }
    }
}
