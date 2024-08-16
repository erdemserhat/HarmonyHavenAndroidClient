package com.erdemserhat.harmonyhaven.dto.responses

import kotlinx.serialization.Serializable

@Serializable
data class NotificationDto(
    val id: Int,
    val title: String,
    val content: String,
    val timeStamp:Long,
    val screenCode:String
)