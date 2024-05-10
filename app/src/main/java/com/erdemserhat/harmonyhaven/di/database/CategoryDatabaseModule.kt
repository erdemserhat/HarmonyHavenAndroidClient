package com.erdemserhat.harmonyhaven.di.database

import com.erdemserhat.harmonyhaven.data.local.AppDatabase
import com.erdemserhat.harmonyhaven.data.local.repository.CategoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object CategoryDatabaseModule {

    @Provides
    @Singleton
    fun provideCategoryRepository(
        appDatabase: AppDatabase
    ):CategoryRepository{
        return CategoryRepository(appDatabase.categoryDao())
    }
}