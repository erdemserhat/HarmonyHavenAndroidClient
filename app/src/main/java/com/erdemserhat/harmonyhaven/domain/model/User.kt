package com.erdemserhat.harmonyhaven.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val name:String,
    val surname:String,
    val email:String,
    val password:String,
    val gender:String,
    val profilePhotoPath:String="",
    val id:Int
)