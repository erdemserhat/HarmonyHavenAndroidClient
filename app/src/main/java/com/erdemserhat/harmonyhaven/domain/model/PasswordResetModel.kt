package com.erdemserhat.harmonyhaven.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class PasswordResetModel(
    val code:String,
    val newPassword:String
)
