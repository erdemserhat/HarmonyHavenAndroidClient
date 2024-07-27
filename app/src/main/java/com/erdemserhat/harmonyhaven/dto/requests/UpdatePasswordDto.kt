package com.erdemserhat.harmonyhaven.dto.requests

import kotlinx.serialization.Serializable

@Serializable
data class UpdatePasswordDto(
    val newPassword:String,
    val currentPassword:String
)
