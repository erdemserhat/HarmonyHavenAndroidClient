package com.erdemserhat.harmonyhaven.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserUpdateModel(
    val userLogin: UserLogin,
    val updatedUserData: User
)

