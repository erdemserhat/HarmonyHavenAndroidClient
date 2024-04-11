package com.erdemserhat.harmonyhaven.dto.requests.password_reset

import kotlinx.serialization.Serializable


@Serializable
data class PasswordResetFinalRequest(
    val uuid:String,
    val password:String
)
