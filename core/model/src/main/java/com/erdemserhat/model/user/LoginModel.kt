package com.erdemserhat.model.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginModel(
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String
)