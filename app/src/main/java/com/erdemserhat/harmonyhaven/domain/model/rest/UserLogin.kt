package com.erdemserhat.harmonyhaven.domain.model.rest

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class UserLogin(
    @SerialName("email")
    val email:String,
    @SerialName("password")
    val password:String
)
