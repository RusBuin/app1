package com.example.siet.presentation

import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.siet.domain.GetDataUseCase
import com.example.siet.domain.SaveUserUseCase
import com.example.siet.domain.models.RegistrationRequest
import com.example.siet.domain.models.RegistrationResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class UserViewModel @Inject constructor(
    private val saveUserUseCase: SaveUserUseCase,
    private val getDataUseCase: GetDataUseCase
) : ViewModel() {

    private val _registrationResult = MutableStateFlow<Result<RegistrationResponse>?>(null)
    val registrationResult: StateFlow<Result<RegistrationResponse>?> = _registrationResult
    private val _isRegistrationSuccessful = MutableStateFlow(false)
    val isRegistrationSuccessful: StateFlow<Boolean> = _isRegistrationSuccessful

    fun saveUser(email: String, password: String) {
        val request = RegistrationRequest(email, password)
        viewModelScope.launch {
            val result = saveUserUseCase.execute(request)
            val response = result.getOrNull()
            if (response!=null) {
                _registrationResult.value = Result.success(response)
                _isRegistrationSuccessful.value = true

            } else {
                _registrationResult.value = Result.failure(result.exceptionOrNull()!!)
                _isRegistrationSuccessful.value = false
            }
        }
    }

    fun observeRegistrationResult(
        progressBar: ProgressBar,
        textViewResult: TextView,
        TAG: String
    ) {
        viewModelScope.launch {
            registrationResult.collect { result ->
                progressBar.visibility = ProgressBar.GONE
                result?.let {
                    if (it.isSuccess) {
                        val response = it.getOrNull()
                        Log.d(TAG, "Registration successful. ID: ${response?.id}, Token: ${response?.token}")
                        textViewResult.text = "Registration successful!"
                    } else {
                        Log.d(TAG, "Registration failed. Error: ${it.exceptionOrNull()?.message}")
                        textViewResult.text = "Registration failed: ${it.exceptionOrNull()?.message}"
                    }
                }
            }
        }
    }
    fun getUserData(): RegistrationRequest {
        return getDataUseCase.execute().getOrThrow()
    }
}





