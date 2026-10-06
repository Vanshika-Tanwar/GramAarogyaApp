package com.example.gramaarogya.Screens.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.gramaarogya.Database.User.UserDao
import com.example.gramaarogya.Repository.Auth.SessionManager
import kotlinx.coroutines.launch

data class UserProfile(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val address: String = "",
    val dob: String = "",
    val gender: String = ""
)

class ProfileViewModel(
    private val userDao: UserDao
) : ViewModel() {

    var profile by mutableStateOf(UserProfile())
        private set

    var isLoading by mutableStateOf(true)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    init {
        fetchProfile()
    }

    fun fetchProfile() {
        viewModelScope.launch {
            isLoading = true
            try {
                val sessionUser = SessionManager.currentUser.value
                val user = if (sessionUser != null) {
                    userDao.getUserByEmail(sessionUser.email) ?: sessionUser
                } else {
                    userDao.getLatestUser()
                }

                if (user != null) {
                    val fullName = listOf(user.firstName, user.lastName)
                        .filter { it.isNotBlank() }
                        .joinToString(" ")

                    profile = UserProfile(
                        name = fullName,
                        email = user.email,
                        phone = user.phone,
                        address = user.address,
                        dob = user.dob,
                        gender = user.gender
                    )
                } else {
                    profile = UserProfile()
                }
            } catch (e: Exception) {
                error = e.message
            } finally {
                isLoading = false
            }
        }
    }
}

class ProfileViewModelFactory(
    private val userDao: UserDao
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(userDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}