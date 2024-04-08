package com.erdemserhat.harmonyhaven.dto.responses

import kotlinx.serialization.Serializable

@Serializable
data class AuthenticationResponse(
    val formValidationResult: ValidationResult,
    val credentialsValidationResult: ValidationResult?,
    val isAuthenticated: Boolean=false,
    val jwt: String?,
)