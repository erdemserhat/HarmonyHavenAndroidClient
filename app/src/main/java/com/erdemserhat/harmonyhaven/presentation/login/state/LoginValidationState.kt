package com.erdemserhat.harmonyhaven.presentation.login.state

data class LoginValidationState(
    val isEmailValid: Boolean = true,
    val isPasswordValid: Boolean = true,
    val isCredentialsValid: Boolean = true,
    val isRegistered: Boolean = true,
)


fun LoginValidationState.getValidationStateByErrorCode(errorCode: Int): LoginValidationState {
    return when (errorCode) {
        101 -> LoginValidationState(isEmailValid = false)
        102, 103 -> LoginValidationState(isPasswordValid = false)
        104 -> LoginValidationState(isCredentialsValid = false)
        105 -> LoginValidationState(isPasswordValid = false)
        else -> LoginValidationState()
    }
}