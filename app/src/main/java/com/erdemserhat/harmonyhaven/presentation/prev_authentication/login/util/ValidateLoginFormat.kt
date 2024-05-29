package com.erdemserhat.harmonyhaven.presentation.prev_authentication.login.util

fun validateLoginFormant(email:String,password:String):LoginValidationError{
    val emailRegex = Regex("^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})")
    return if (email.matches(emailRegex)) {
        if (password.length >= 8) {
            LoginValidationError.NoError
        } else {
            LoginValidationError.ShortPassword
        }

    } else {
        LoginValidationError.InvalidFormatEmail
    }

}