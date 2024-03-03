package com.erdemserhat.harmonyhaven.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class AuthenticatedUser(
    @SerialName("isUserExist")
    val isUserExist:Boolean,

    @SerialName("name")
    val name:String,

    @SerialName("surname")
    val surname:String,
)
