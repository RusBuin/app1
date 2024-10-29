package com.example.siet.presentation.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.siet.domain.GetUsersUseCase
import com.example.siet.domain.models.UserOfList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel

class ListViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    private val _users = MutableStateFlow<PagingData<UserOfList>>(PagingData.empty())
    val users: StateFlow<PagingData<UserOfList>> = _users

    private val _followedUsers = MutableStateFlow<Set<Int>>(emptySet())

    fun loadUsers() {
        viewModelScope.launch {
            getUsersUseCase()
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _users.value = pagingData
                }
        }
    }

    fun followUser(user: UserOfList) {
        _followedUsers.value = _followedUsers.value.toMutableSet().apply {
            add(user.id)
        }
    }

    fun unfollowUser(user: UserOfList) {
        _followedUsers.value = _followedUsers.value.toMutableSet().apply {
            remove(user.id)
        }
    }


}
