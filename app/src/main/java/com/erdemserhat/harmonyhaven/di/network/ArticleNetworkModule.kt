package com.erdemserhat.harmonyhaven.di.network

import com.erdemserhat.harmonyhaven.data.api.article.ArticleApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ArticleNetworkModule {

    @Provides
    @Singleton
    fun provideArticleApiService(retrofit: Retrofit):ArticleApiService {
        return retrofit.create(ArticleApiService::class.java)
    }

}