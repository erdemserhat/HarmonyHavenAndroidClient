package com.erdemserhat.harmonyhaven.dto.requests.password_reset

import kotlinx.serialization.Serializable

@Serializable
data class PasswordResetAuthenticateRequest(
    val code:String,
    val email:String
)
