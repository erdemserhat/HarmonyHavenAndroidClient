package com.erdemserhat.harmonyhaven.dto.requests

import kotlinx.serialization.Serializable

@Serializable
class UserInformationSchema(
    val name: String,
    var surname: String,
    val email: String,
    val password: String,
    val gender: String,
)