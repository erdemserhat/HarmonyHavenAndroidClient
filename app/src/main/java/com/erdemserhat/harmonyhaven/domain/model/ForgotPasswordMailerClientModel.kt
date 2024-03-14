package com.erdemserhat.harmonyhaven.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ForgotPasswordMailerClientModel(
    @SerialName("email")
    val email:String
)
