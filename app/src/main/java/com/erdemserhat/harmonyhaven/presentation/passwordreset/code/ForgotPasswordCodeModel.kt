package com.erdemserhat.harmonyhaven.presentation.passwordreset.code

data class ForgotPasswordCodeModel(
    val canNavigateTo:Boolean = false,
    val isLoading:Boolean = false,
    val codeWarning:String= ""
)
