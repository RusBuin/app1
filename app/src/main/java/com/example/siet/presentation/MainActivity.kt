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
        val userId = sharedPreferences.getString("userId", null)

//        if (userId != null) {
//            val intent = Intent(this, UserListActivity::class.java)
//            startActivity(intent)
//        } else {
            val intent = Intent(this, RegisterUserActivity::class.java)
            startActivity(intent)
//        }
//        finish()
    }
}