package com.erdemserhat.dto.responses

import kotlinx.serialization.Serializable

@Serializable
data class UserInformationDto(
    val name:String="",
    val email:String=""
)
