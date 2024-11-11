package com.erdemserhat.harmonyhaven.domain.usecase.quote

class QuoteUseCases(
    val deleteQuoteById: DeleteQuoteByIdUseCase,
    val getQuote: GetQuotesUseCase,
    val likeQuote: LikeQuoteUseCase,
    val removeLike: RemoveLikeQuoteUseCase,
)