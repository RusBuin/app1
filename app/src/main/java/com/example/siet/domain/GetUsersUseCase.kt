package com.example.siet.domain

import androidx.paging.PagingData
import com.example.siet.domain.models.UserOfList
import com.example.siet.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<PagingData<UserOfList>> {
        return userRepository.getUsers()
    }
}

