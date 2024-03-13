package com.erdemserhat.harmonyhaven.presentation.register

data class RegisterState(
    val isLoading:Boolean = false,
    var registerWarning:String ="",
    val canNavigateTo:Boolean =false
)
