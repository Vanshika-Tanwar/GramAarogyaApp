package com.example.gramaarogya.Repository.Auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gramaarogya.Screens.SignIn.SignInViewModel
import com.example.gramaarogya.Screens.SignUp.SignUpViewModel

class AuthViewModelFactory(
    private val repository: AuthRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        return when {

            modelClass.isAssignableFrom(
                SignUpViewModel::class.java
            ) -> SignUpViewModel(repository)

            modelClass.isAssignableFrom(
                SignInViewModel::class.java
            ) -> SignInViewModel(repository)

            else -> throw IllegalArgumentException(
                "Unknown ViewModel class"
            )
        } as T
    }
}