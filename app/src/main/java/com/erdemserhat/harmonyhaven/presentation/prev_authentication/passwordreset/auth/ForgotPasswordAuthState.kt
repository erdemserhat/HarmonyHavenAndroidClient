package com.erdemserhat.harmonyhaven.presentation.prev_authentication.passwordreset.auth

data class ForgotPasswordAuthState(
    val canNavigateTo:Boolean = false,
    val isLoading:Boolean = false,
    val authWarning:String= "",
    val uuid:String="N/A",
    val isError:Boolean = false

)
