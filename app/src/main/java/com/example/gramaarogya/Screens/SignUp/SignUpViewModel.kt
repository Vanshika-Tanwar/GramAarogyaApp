package com.example.gramaarogya.Screens.SignUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gramaarogya.R
import com.example.gramaarogya.Repository.Auth.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())

    val uiState = _uiState.asStateFlow()

    fun canRegister(): Boolean {
        val state = _uiState.value

        return state.firstName.isNotBlank() &&
                state.lastName.isNotBlank() &&
                state.email.isNotBlank() &&
                state.password.isNotBlank() &&
                state.termsAccepted
    }

    fun onFirstNameChange(value: String) {
        _uiState.update {
            it.copy(firstName = value)
        }
    }

    fun onLastNameChange(value: String) {
        _uiState.update {
            it.copy(lastName = value)
        }
    }

    fun onEmailChange(value: String) {
        _uiState.update {
            it.copy(email = value)
        }
    }

    fun onPasswordChange(value: String) {
        _uiState.update {
            it.copy(password = value)
        }
    }

    fun onTermsChanged(value: Boolean) {
        _uiState.update {
            it.copy(termsAccepted = value)
        }
    }

    fun registerUser(
        onSuccess: () -> Unit
    ) {

        val state = _uiState.value

        if (
            state.firstName.isBlank() ||
            state.lastName.isBlank() ||
            state.email.isBlank() ||
            state.password.isBlank()
        ) {
            _uiState.update {
                it.copy(
                    errorRes =  R.string.auth_err_fill_all
                )
            }

            return
        }

        if (!state.termsAccepted) {
            _uiState.update {
                it.copy(
                    errorRes =  R.string.auth_err_accept_terms
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

            val registeredUser = repository.registerUser(
                firstName = state.firstName,
                lastName = state.lastName,
                email = state.email,
                password = state.password
            )

            if (registeredUser != null) {

                _uiState.update {
                    it.copy(isLoading = false)
                }

                onSuccess()

            } else {

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorRes = R.string.auth_err_email_exists
                    )
                }
            }
        }
    }
}