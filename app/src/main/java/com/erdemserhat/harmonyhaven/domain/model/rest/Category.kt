package com.erdemserhat.harmonyhaven.domain.model.rest

import kotlinx.serialization.Serializable


@Serializable
data class Category(
    val id:Int,
    val name:String,
    val imagePath:String

)