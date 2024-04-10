package com.erdemserhat.harmonyhaven.dto.requests

import kotlinx.serialization.Serializable

@Serializable
data class FcmSetupRequest(
    val fcmID:String
)
