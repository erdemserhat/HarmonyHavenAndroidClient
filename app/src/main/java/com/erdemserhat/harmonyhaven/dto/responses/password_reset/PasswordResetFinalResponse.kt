package com.erdemserhat.harmonyhaven.dto.responses.password_reset

import kotlinx.serialization.Serializable


@Serializable
data class PasswordResetFinalResponse(
    val result:Boolean,
    val message:String
)