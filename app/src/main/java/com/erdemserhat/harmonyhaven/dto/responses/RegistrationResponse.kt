package com.erdemserhat.harmonyhaven.dto.responses

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationResponse(
    val formValidationResult: ValidationResult,
    val isRegistered: Boolean,
)

