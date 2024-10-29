package com.example.siet.presentation

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.siet.data.repository.UserRepositoryImpl
import com.example.siet.domain.GetDataUseCase
import com.example.siet.domain.SaveUserUseCase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences: SharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE)

        val isUserRegistered = sharedPreferences.getBoolean("isUserRegistered", false)
        val token = sharedPreferences.getString("token", null)

        if (isUserRegistered && token != null) {
            val intent = Intent(this, FollowsListActivity::class.java) // Переход к списку пользователей
            startActivity(intent)
        } else {
            val intent = Intent(this, RegisterUserActivity::class.java)
            startActivity(intent)
        }

        finish()
    }
}
