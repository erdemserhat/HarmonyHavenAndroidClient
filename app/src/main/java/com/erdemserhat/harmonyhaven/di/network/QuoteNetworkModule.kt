package com.erdemserhat.harmonyhaven.di.network

import com.erdemserhat.harmonyhaven.data.api.quote.QuoteApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


/**
 * Dagger Hilt module for providing network-related dependencies related to quotes.
 *
 * This module is used to provide a singleton instance of the [QuoteApiService] that can be
 * injected wherever needed in the application. It uses Dagger Hilt for dependency injection.
 */
@Module
@InstallIn(SingletonComponent::class)
object QuoteNetworkModule {

    /**
     * Provides a singleton instance of [QuoteApiService].
     *
     * This method creates an instance of [QuoteApiService] using the provided [Retrofit] instance.
     * The [QuoteApiService] is used to make network requests related to quotes, such as fetching
     * a list of quotes from the server.
     *
     * @param retrofit The [Retrofit] instance used to create the [QuoteApiService].
     * @return A singleton instance of [QuoteApiService].
     */
    @Provides
    @Singleton
    fun provideQuoteApiService(retrofit: Retrofit): QuoteApiService {
        return retrofit.create(QuoteApiService::class.java)
    }
}
