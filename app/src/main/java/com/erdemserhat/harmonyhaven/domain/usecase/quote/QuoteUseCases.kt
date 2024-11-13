package com.erdemserhat.harmonyhaven.domain.usecase.quote

class QuoteUseCases(
    val deleteQuoteById: DeleteQuoteById,
    val getQuote: GetQuotes,
    val likeQuote: LikeQuote,
    val removeLike: RemoveLikeQuote,
    val getLikedQuotes: GetLikedQuotes,
)