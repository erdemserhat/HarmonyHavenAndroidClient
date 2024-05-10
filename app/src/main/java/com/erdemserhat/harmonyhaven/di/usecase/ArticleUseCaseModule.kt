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

@Module
@InstallIn(SingletonComponent::class)
object ArticleUseCaseModule {
    @Provides
    @Singleton
    fun provideArticleUseCases(
        articleApiService: ArticleApiService
    ): ArticleUseCases {
        return ArticleUseCases(
            Categories(),
            GetArticleById(),
            GetRecentArticles(),
            GetArticlesByCategory(),
            GetAllArticles(),
            GetArticles(articleApiService),
            GetCategories(articleApiService)
        )
    }
}