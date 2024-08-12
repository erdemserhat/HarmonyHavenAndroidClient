package com.erdemserhat.harmonyhaven.di.network

import com.erdemserhat.harmonyhaven.data.api.article.ArticleApiService
import com.erdemserhat.harmonyhaven.data.api.quote.QuoteApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object QuoteNetworkModule {

    @Provides
    @Singleton
    fun provideQuoteApiService(retrofit: Retrofit):QuoteApiService {
        return retrofit.create(QuoteApiService::class.java)
    }

}