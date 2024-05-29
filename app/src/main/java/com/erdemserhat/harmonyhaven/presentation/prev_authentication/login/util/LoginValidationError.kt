package com.erdemserhat.harmonyhaven.presentation.prev_authentication.login.util

sealed class LoginValidationError(val errorMessage:String="") {
    data object ShortPassword: LoginValidationError("Password must be at least 8 characters.")
    data object InvalidFormatEmail: LoginValidationError("Please enter a valid email.")
    data object NoError: LoginValidationError("")
}