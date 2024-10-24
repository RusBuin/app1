//package com.example.siet.presentation
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.siet.domain.GetUsersUseCase
//import com.example.siet.domain.models.User
//import com.example.siet.domain.models.UserOfList
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//
//class ListViewModel(private val getUsersUseCase: GetUsersUseCase) : ViewModel() {
//
//    private val _users = MutableStateFlow<List<UserOfList>>(emptyList())
//    val users: StateFlow<List<UserOfList>> = _users
//
//    fun loadUsers() {
//        viewModelScope.launch {
//            val usersFlow = getUsersUseCase.execute()
//            usersFlow.collect { userList ->
//                _users.value = userList
//            }
//        }
//    }
//}
