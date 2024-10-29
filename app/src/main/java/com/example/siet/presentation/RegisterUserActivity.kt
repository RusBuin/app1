package com.example.siet.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.siet.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class RegisterUserActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration)

        Log.d(TAG, "onCreate: Start registration activity")

        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val buttonRegister = findViewById<Button>(R.id.buttonRegister)
        val textViewResult = findViewById<TextView>(R.id.textViewResult)

        buttonRegister.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            userViewModel.saveUser(email, password)
            progressBar.visibility = ProgressBar.VISIBLE
        }

        userViewModel.observeRegistrationResult(progressBar, textViewResult, TAG)
        lifecycleScope.launch {
            userViewModel.isRegistrationSuccessful.collect { isSuccessful ->
                if (isSuccessful) {
                    progressBar.visibility = ProgressBar.GONE
                    val intent = Intent(this@RegisterUserActivity, FollowsListActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

    }

    companion object {
        private const val TAG = "RegisterUserActivity"
    }
}

