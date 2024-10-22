package com.erdemserhat.harmonyhaven.dto.responses

import kotlinx.serialization.Serializable


@Serializable
data class GoogleAuthenticationResponse(
    val jwt:String
)

