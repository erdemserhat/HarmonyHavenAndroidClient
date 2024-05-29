package com.erdemserhat.harmonyhaven.presentation.prev_authentication.passwordreset.mail

data class ForgotPasswordMailState(
    val isLoading:Boolean = false,
    var mailWarning:String ="",
    val canNavigateTo:Boolean =false,
    val isError:Boolean=false
)
