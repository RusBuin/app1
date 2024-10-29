package com.example.siet.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.siet.domain.models.RegistrationRequest
import com.example.siet.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _registrationSuccess = MutableStateFlow(false)
    val registrationSuccess: StateFlow<Boolean> = _registrationSuccess

    fun saveUser(email: String, password: String) {
        val request = RegistrationRequest(email, password)
        viewModelScope.launch {
            val result = userRepository.registerUserViaApi(request)
            _registrationSuccess.value = result.isSuccess
        }
    }
}
