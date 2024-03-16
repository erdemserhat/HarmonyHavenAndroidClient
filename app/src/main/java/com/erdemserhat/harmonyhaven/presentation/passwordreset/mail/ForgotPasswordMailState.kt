package com.erdemserhat.harmonyhaven.presentation.passwordreset.mail

data class ForgotPasswordMailState(
    val isLoading:Boolean = false,
    var mailWarning:String ="",
    val canNavigateTo:Boolean =false
)
