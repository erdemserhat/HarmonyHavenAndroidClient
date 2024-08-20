package com.erdemserhat.harmonyhaven.di.network

import com.erdemserhat.harmonyhaven.data.api.article.ArticleApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


/**
 * Dagger Hilt module for providing network-related dependencies for articles.
 *
 * This module provides an instance of the [ArticleApiService] for making API requests related to articles.
 * It is installed in the singleton component, ensuring that the provided dependencies are singletons
 * throughout the application's lifecycle.
 */
@Module
@InstallIn(SingletonComponent::class)
object ArticleNetworkModule {

    /**
     * Provides an instance of [ArticleApiService].
     *
     * This method creates and provides a singleton instance of [ArticleApiService] using the provided [Retrofit] instance.
     * The [ArticleApiService] is used for making network requests related to articles, such as fetching article data
     * from a remote server.
     *
     * @param retrofit An instance of [Retrofit] used to create the [ArticleApiService] instance.
     * @return A singleton instance of [ArticleApiService] for interacting with the article-related API endpoints.
     */
    @Provides
    @Singleton
    fun provideArticleApiService(retrofit: Retrofit): ArticleApiService {
        return retrofit.create(ArticleApiService::class.java)
    }
}
