package com.erdemserhat.harmonyhaven.presentation.register.state

data class RegisterState(
    val isLoading:Boolean = false,
    var registerWarning:String ="",
    val canNavigateTo:Boolean =false,
    val registerValidationState: RegisterValidationState = RegisterValidationState()
)


