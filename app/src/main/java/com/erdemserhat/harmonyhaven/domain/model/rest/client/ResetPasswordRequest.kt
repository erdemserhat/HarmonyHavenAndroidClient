package com.erdemserhat.harmonyhaven.domain.model.rest.client

import kotlinx.serialization.Serializable


@Serializable
data class
ArticleResponseTypeResetPasswordRequest(
    val email:String
)
