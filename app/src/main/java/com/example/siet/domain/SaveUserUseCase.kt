package com.example.siet.domain

import com.example.siet.domain.models.RegistrationRequest
import com.example.siet.domain.models.RegistrationResponse
import com.example.siet.domain.repository.UserRepository
import javax.inject.Inject

class SaveUserUseCase @Inject constructor (private val userRepository: UserRepository) {

    suspend fun execute(request: RegistrationRequest): Result<RegistrationResponse> {
        val localSaveResult = userRepository.saveDataUser(request)
        if (!localSaveResult) {
            return Result.failure(Exception("Failed to save user locally"))
        }

        return userRepository.registerUserViaApi(request)
    }
}
