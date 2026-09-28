package com.example.gramaarogya.Repository.Auth

import com.example.gramaarogya.Database.User.UserEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object SessionManager {

    private val _currentUser =
        MutableStateFlow<UserEntity?>(null)

    val currentUser =
        _currentUser.asStateFlow()

    fun login(user: UserEntity) {
        _currentUser.value = user
    }

    fun logout() {
        _currentUser.value = null
    }
}