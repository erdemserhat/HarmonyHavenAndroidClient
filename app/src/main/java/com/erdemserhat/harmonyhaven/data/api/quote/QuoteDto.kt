package com.erdemserhat.harmonyhaven.data.api.quote

import com.erdemserhat.harmonyhaven.domain.model.rest.DailyQuote

data class QuoteDto(
    val quote: String
)

fun QuoteDto.toDomainModel(): DailyQuote {
    return DailyQuote(
        quote = this.quote
    )
} 