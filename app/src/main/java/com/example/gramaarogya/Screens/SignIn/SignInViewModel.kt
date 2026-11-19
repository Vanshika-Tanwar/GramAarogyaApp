package com.example.gramaarogya.Screens.SignIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gramaarogya.R
import com.example.gramaarogya.Repository.Auth.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.gramaarogya.Repository.Auth.SessionManager
class SignInViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    fun onEmailChange(value: String) {
        _uiState.update {
            it.copy(
                email = value,
                errorRes = null
            )
        }
    }

    fun onPasswordChange(value: String) {
        _uiState.update {
            it.copy(
                password = value,
                errorRes = null
            )
        }
    }

    fun canSignIn(): Boolean {
        val state = _uiState.value

        return state.email.isNotBlank() &&
                state.password.isNotBlank()
    }


    fun signIn(
        onSuccess: () -> Unit
    ) {

        val state = _uiState.value

        if (!canSignIn()) {

            _uiState.update {
                it.copy(
                    errorRes = R.string.auth_err_enter_credentials
                )
            }

            return
        }

        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorRes = null
                )
            }

            val user = repository.login(
                email = state.email.trim(),
                password = state.password
            )

            if (user != null) {

                // Save currently logged-in user
                SessionManager.login(user)

                _uiState.update {
                    it.copy(
                        isLoading = false
                    )
                }

                onSuccess()

            } else {

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorRes = R.string.auth_err_invalid_credentials
                    )
                }
            }
        }
    }
}