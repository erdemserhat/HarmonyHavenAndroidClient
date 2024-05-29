package com.erdemserhat.harmonyhaven.presentation.prev_authentication.passwordreset.reset


data class ForgotPasswordResetState(
    val isLoading:Boolean = false,
    var resetWarning:String ="",
    val canNavigateTo:Boolean =false,
    val isError:Boolean = false
)
