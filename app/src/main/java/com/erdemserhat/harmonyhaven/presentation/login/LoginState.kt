package com.erdemserhat.harmonyhaven.presentation.login

data class LoginState(
    val isRememberSelected: Boolean = false,
    val isShowPasswordSelected: Boolean = false,
    val canNavigateToDashBoard:Boolean = false,
    val email: String = "",
    val password: String = "",
    var loginWarning:String="",
)