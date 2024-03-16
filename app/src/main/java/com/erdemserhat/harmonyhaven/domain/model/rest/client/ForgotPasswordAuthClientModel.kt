package com.erdemserhat.harmonyhaven.domain.model.rest.client

import kotlinx.serialization.Serializable


@Serializable
data class ForgotPasswordAuthClientModel(
    val code:String,
    val email:String
)
