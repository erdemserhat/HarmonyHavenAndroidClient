package com.erdemserhat.harmonyhaven.presentation.prev_authentication.register.state

data class RegisterState(
    val isLoading:Boolean = false,
    var registerWarning:String ="",
    val canNavigateTo:Boolean =false,
    val registerValidationState: RegisterValidationState = RegisterValidationState()
)


