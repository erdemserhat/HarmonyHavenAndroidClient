package com.erdemserhat.harmonyhaven.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class ForgotPasswordResetClientModel(
    val uuid:String,
    val password:String
)
