package com.erdemserhat.harmonyhaven.presentation.register

data class RegisterState(
    val isLoading:Boolean = false,
    val loginWarning:String ="",
    val canNavigateTo:Boolean =false
)
