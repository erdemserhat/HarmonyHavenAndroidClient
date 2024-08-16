package com.erdemserhat.harmonyhaven.dto.responses
import kotlinx.serialization.Serializable


@Serializable
data class Quote(
    val id:Int,
    val quote:String,
    val writer:String,
    val imageUrl:String
)
