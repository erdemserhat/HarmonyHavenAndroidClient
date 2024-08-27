package com.erdemserhat.harmonyhaven.domain.model.rest

import kotlinx.serialization.Serializable


@Serializable
data class RequestResultUUID (
    val result:Boolean,
    val message: String,
    val isLoading:Boolean,
    val uuid: String="N/A"
)