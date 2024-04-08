package com.erdemserhat.harmonyhaven.dto.responses

import kotlinx.serialization.Serializable

@Serializable
data class ValidationResult(
    val isValid: Boolean = true,
    val errorMessage: String = "",
    val errorCode: Int = 0
)
