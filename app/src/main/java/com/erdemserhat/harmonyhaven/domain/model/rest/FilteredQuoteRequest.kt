package com.erdemserhat.harmonyhaven.domain.model.rest

import kotlinx.serialization.Serializable

@Serializable
data class FilteredQuoteRequest(
    val categories: List<Int>,
    val page: Int = 1,
    val pageSize: Int = 10,
    val seed:Int
)