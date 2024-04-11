package com.erdemserhat.harmonyhaven.dto.responses.password_reset

import kotlinx.serialization.Serializable

@Serializable
data class PasswordResetAuthenticateResponse(
    val result: Boolean,
    val message: String,
    val uuid: String = "-"
)