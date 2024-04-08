package com.erdemserhat.harmonyhaven.presentation.login.state

data class ValidationState(
    val isEmailValid: Boolean = true,
    val isPasswordValid: Boolean = true,
    val isCredentialsValid: Boolean = true,
    val isRegistered: Boolean = true,
)


fun ValidationState.getValidationStateByErrorCode(errorCode: Int): ValidationState {
    return when (errorCode) {
        101 -> ValidationState(isEmailValid = false)
        102, 103 -> ValidationState(isPasswordValid = false)
        104 -> ValidationState(isCredentialsValid = false)
        105 -> ValidationState(isPasswordValid = false)
        else -> ValidationState()
    }
}