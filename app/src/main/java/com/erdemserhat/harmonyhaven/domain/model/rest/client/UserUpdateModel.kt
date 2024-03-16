package com.erdemserhat.harmonyhaven.domain.model.rest.client

import com.erdemserhat.harmonyhaven.domain.model.rest.User
import kotlinx.serialization.Serializable

@Serializable
data class UserUpdateModel(
    val userLogin: UserLogin,
    val updatedUserData: User
)

