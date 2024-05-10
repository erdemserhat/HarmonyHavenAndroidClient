package com.erdemserhat.harmonyhaven.di.database

import com.erdemserhat.harmonyhaven.data.local.AppDatabase
import com.erdemserhat.harmonyhaven.data.local.repository.JwtTokenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object JwtDatabaseModule {
    @Provides
    @Singleton
    fun provideJwtTokenRepository(
        appDatabase: AppDatabase
    ): JwtTokenRepository {
        return JwtTokenRepository(appDatabase.jwtTokenDao())
    }


}