package com.example.siet.domain

import com.example.siet.domain.models.RegistrationRequest
import com.example.siet.domain.repository.UserRepository
import javax.inject.Inject

class GetDataUseCase @Inject constructor  (private val userRepository: UserRepository) {

    fun execute(): Result<RegistrationRequest> {
        return try {
            val registrationRequest = userRepository.getName()
            Result.success(registrationRequest)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
