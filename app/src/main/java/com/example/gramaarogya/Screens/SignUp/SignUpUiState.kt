package com.example.gramaarogya.Screens.SignUp

import androidx.annotation.StringRes

data class SignUpUiState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val termsAccepted: Boolean = false,
    val isLoading: Boolean = false,
    @StringRes val errorRes: Int? = null
)