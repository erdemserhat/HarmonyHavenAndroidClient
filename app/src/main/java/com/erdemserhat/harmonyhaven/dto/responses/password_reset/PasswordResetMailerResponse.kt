package com.erdemserhat.harmonyhaven.dto.responses.password_reset

import kotlinx.serialization.Serializable

@Serializable
data class PasswordResetMailerResponse(
    val result: Boolean,
    val message: String
)
