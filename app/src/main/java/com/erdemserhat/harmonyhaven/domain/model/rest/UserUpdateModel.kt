package com.erdemserhat.harmonyhaven.domain.model.rest

import kotlinx.serialization.Serializable

@Serializable
data class UserUpdateModel(
    val userLogin: UserLogin,
    val updatedUserData: User
)

