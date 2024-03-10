package com.erdemserhat.harmonyhaven.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class ResetPasswordRequest(
    val email:String
)
