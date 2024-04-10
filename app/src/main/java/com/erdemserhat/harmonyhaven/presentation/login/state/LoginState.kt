package com.erdemserhat.harmonyhaven.presentation.login.state

data class LoginState(
    val isRememberSelected: Boolean = false,
    val canNavigateToDashBoard:Boolean = false,
    val isLoading:Boolean = false,
    val email: String = "",
    val password: String = "",
    var loginWarning:String="",
    var validationState: LoginValidationState = LoginValidationState()


    //form results ::

)
