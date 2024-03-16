package com.erdemserhat.harmonyhaven.presentation.passwordreset.reset


data class ForgotPasswordResetState(
    val isLoading:Boolean = false,
    var resetWarning:String ="",
    val canNavigateTo:Boolean =false
)
