package com.erdemserhat.harmonyhaven.di.database

import com.erdemserhat.harmonyhaven.data.local.AppDatabase
import com.erdemserhat.harmonyhaven.data.local.repository.ArticleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module for providing dependencies related to the article database.
 *
 * This module is used to provide instances of repository classes that interact with the database.
 * It is installed in the singleton component, meaning the provided dependencies will be singletons
 * throughout the application's lifecycle.
 */
@Module
@InstallIn(SingletonComponent::class)
object ArticleDatabaseModule {

    /**
     * Provides an instance of [ArticleRepository].
     *
     * This method creates and provides a singleton instance of [ArticleRepository] using the provided
     * [AppDatabase] instance. The [ArticleRepository] will use the [ArticleDao] from the database to perform
     * database operations related to articles.
     *
     * @param appDatabase An instance of [AppDatabase] used to access the [ArticleDao].
     * @return A singleton instance of [ArticleRepository] for managing article data.
     */
    @Provides
    @Singleton
    fun provideArticleRepository(
        appDatabase: AppDatabase
    ): ArticleRepository {
        return ArticleRepository(appDatabase.articleDao())
    }
}
