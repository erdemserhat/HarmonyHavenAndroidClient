package com.erdemserhat.harmonyhaven.presentation.post_authentication.profile

import com.erdemserhat.harmonyhaven.dto.responses.Quote

data class LikedQuotesState(
    val isLoading:Boolean = false,
    val likedQuotes:List<Quote> = listOf()
)