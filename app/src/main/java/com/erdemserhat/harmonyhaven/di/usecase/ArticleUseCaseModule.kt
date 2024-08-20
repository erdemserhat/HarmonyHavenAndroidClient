package com.erdemserhat.harmonyhaven.di.usecase

import com.erdemserhat.harmonyhaven.data.api.article.ArticleApiService
import com.erdemserhat.harmonyhaven.domain.usecase.article.ArticleUseCases
import com.erdemserhat.harmonyhaven.domain.usecase.article.Categories
import com.erdemserhat.harmonyhaven.domain.usecase.article.GetAllArticles
import com.erdemserhat.harmonyhaven.domain.usecase.article.GetArticleById
import com.erdemserhat.harmonyhaven.domain.usecase.article.GetArticles
import com.erdemserhat.harmonyhaven.domain.usecase.article.GetArticlesByCategory
import com.erdemserhat.harmonyhaven.domain.usecase.article.GetCategories
import com.erdemserhat.harmonyhaven.domain.usecase.article.GetRecentArticles
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module for providing use cases related to articles.
 *
 * This module is responsible for providing an instance of [ArticleUseCases], which aggregates
 * various use case classes for handling article-related operations. It uses Dagger Hilt for dependency
 * injection to ensure that these use cases are available as singletons throughout the application.
 */
@Module
@InstallIn(SingletonComponent::class)
object ArticleUseCaseModule {

    /**
     * Provides a singleton instance of [ArticleUseCases].
     *
     * This method creates an instance of [ArticleUseCases] by injecting various use case classes.
     * It also provides the [ArticleApiService] required for making network requests related to articles.
     *
     * @param articleApiService An instance of [ArticleApiService] used for article-related API operations.
     * @return A singleton instance of [ArticleUseCases] that aggregates all article-related use cases.
     */
    @Provides
    @Singleton
    fun provideArticleUseCases(
        articleApiService: ArticleApiService
    ): ArticleUseCases {
        return ArticleUseCases(
            Categories(),
            GetArticleById(articleApiService),
            GetRecentArticles(),
            GetArticlesByCategory(),
            GetAllArticles(),
            GetArticles(articleApiService),
            GetCategories(articleApiService)
        )
    }
}
