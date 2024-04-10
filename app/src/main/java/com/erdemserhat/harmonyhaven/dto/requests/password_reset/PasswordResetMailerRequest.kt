package com.erdemserhat.harmonyhaven.dto.requests.password_reset

import kotlinx.serialization.Serializable


@Serializable
data class PasswordResetMailerRequest(
    val email:String
)
