package com.erdemserhat.harmonyhaven.di.database

import com.erdemserhat.harmonyhaven.data.local.AppDatabase
import com.erdemserhat.harmonyhaven.data.local.repository.CategoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module for providing dependencies related to the category database.
 *
 * This module is used to provide instances of repository classes that interact with the database.
 * It is installed in the singleton component, ensuring that the provided dependencies are singletons
 * throughout the application's lifecycle.
 */
@Module
@InstallIn(SingletonComponent::class)
object CategoryDatabaseModule {

    /**
     * Provides an instance of [CategoryRepository].
     *
     * This method creates and provides a singleton instance of [CategoryRepository] using the provided
     * [AppDatabase] instance. The [CategoryRepository] will use the [CategoryDao] from the database to perform
     * database operations related to categories.
     *
     * @param appDatabase An instance of [AppDatabase] used to access the [CategoryDao].
     * @return A singleton instance of [CategoryRepository] for managing category data.
     */
    @Provides
    @Singleton
    fun provideCategoryRepository(
        appDatabase: AppDatabase
    ): CategoryRepository {
        return CategoryRepository(appDatabase.categoryDao())
    }
}
