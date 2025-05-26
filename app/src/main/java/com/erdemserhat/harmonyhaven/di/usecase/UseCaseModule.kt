package com.erdemserhat.harmonyhaven.di.usecase

import com.erdemserhat.harmonyhaven.data.api.quote.QuoteApi
import com.erdemserhat.harmonyhaven.domain.usecase.GetDailyQuoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideQuoteApi(retrofit: Retrofit): QuoteApi {
        return retrofit.create(QuoteApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGetDailyQuoteUseCase(quoteApi: QuoteApi): GetDailyQuoteUseCase {
        return GetDailyQuoteUseCase(quoteApi)
    }
} 