package com.erdemserhat.harmonyhaven.di.database

import com.erdemserhat.harmonyhaven.data.local.AppDatabase
import com.erdemserhat.harmonyhaven.data.local.repository.ArticleRepository
import com.erdemserhat.harmonyhaven.data.local.repository.JwtTokenRepository
import com.erdemserhat.harmonyhaven.domain.model.rest.ArticleResponseType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ArticleDatabaseModule {

    @Provides
    @Singleton
    fun provideArticleRepository(
        appDatabase: AppDatabase
    ):ArticleRepository{
          return ArticleRepository(appDatabase.articleDao())

    }
}