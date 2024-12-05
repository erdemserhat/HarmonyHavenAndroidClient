package com.erdemserhat.harmonyhaven.di.cache

import com.erdemserhat.harmonyhaven.data.local.entities.QuoteEntity
import com.erdemserhat.harmonyhaven.dto.responses.Quote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DefaultCacheModule {

    @Provides
    @Singleton
    @Named("DefaultQuoteCache")
    fun provideDefaultQuoteCache(): List<QuoteEntity> {
        return listOf(
            QuoteEntity(
                imageUrl = "https://harmonyhaven.erdemserhat.com/sources/quote_assets/30112024/kitabakacis/kitabakacis-20241130-0006.mp4"

            )
        )
    }


}