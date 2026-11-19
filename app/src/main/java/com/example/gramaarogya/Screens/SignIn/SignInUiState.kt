package com.example.gramaarogya.Screens.SignIn

import androidx.annotation.StringRes

data class SignInUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    @StringRes val errorRes: Int? = null
)