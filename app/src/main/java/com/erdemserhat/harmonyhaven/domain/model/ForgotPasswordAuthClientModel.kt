package com.erdemserhat.harmonyhaven.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class ForgotPasswordAuthClientModel(
    val code:String,
    val email:String
)
