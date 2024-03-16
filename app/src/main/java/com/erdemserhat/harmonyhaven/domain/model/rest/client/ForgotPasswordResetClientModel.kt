package com.erdemserhat.harmonyhaven.domain.model.rest.client

import kotlinx.serialization.Serializable


@Serializable
data class ForgotPasswordResetClientModel(
    val uuid:String,
    val password:String
)
