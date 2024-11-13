package com.erdemserhat.harmonyhaven.di.usecase

import com.erdemserhat.harmonyhaven.data.api.quote.QuoteApiService
import com.erdemserhat.harmonyhaven.domain.usecase.quote.DeleteQuoteById
import com.erdemserhat.harmonyhaven.domain.usecase.quote.GetLikedQuotes
import com.erdemserhat.harmonyhaven.domain.usecase.quote.GetQuotes
import com.erdemserhat.harmonyhaven.domain.usecase.quote.LikeQuote
import com.erdemserhat.harmonyhaven.domain.usecase.quote.QuoteUseCases
import com.erdemserhat.harmonyhaven.domain.usecase.quote.RemoveLikeQuote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module for providing use cases related to quotes.
 *
 * This module is responsible for providing an instance of [GetQuotes], which handles the retrieval of quotes.
 * It uses Dagger Hilt for dependency injection to ensure that the use case is available as a singleton throughout the application.
 */
@Module
@InstallIn(SingletonComponent::class)
object QuoteUseCaseModule {

    /**
     * Provides a singleton instance of [GetQuotes].
     *
     * This method creates an instance of [GetQuotes] by injecting the necessary [QuoteApiService]
     * used for making network requests related to quotes.
     *
     * @param quoteApiService An instance of [QuoteApiService] used for quote-related API operations.
     * @return A singleton instance of [GetQuotes] for retrieving quotes.
     */

    @Provides
    @Singleton
    fun provideQuoteUseCase(quoteApiService: QuoteApiService): QuoteUseCases {
        return QuoteUseCases(
            deleteQuoteById = DeleteQuoteById(quoteApiService),
            likeQuote = LikeQuote(quoteApiService),
            getQuote = GetQuotes(quoteApiService),
            removeLike = RemoveLikeQuote(quoteApiService),
            getLikedQuotes = GetLikedQuotes(quoteApiService)


        )
    }

}
