package com.erdemserhat.harmonyhaven.dto.requests

import kotlinx.serialization.Serializable

@Serializable
data class UserAuthenticationRequest(
    val email:String,
    val password:String
)
