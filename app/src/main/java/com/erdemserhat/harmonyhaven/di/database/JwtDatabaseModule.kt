package com.erdemserhat.harmonyhaven.di.database

import com.erdemserhat.harmonyhaven.data.local.AppDatabase
import com.erdemserhat.harmonyhaven.data.local.repository.JwtTokenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * Dagger Hilt module for providing dependencies related to JWT token management.
 *
 * This module provides instances of repository classes that interact with the JWT token database.
 * It is installed in the singleton component, ensuring that the provided dependencies are singletons
 * throughout the application's lifecycle.
 */
@Module
@InstallIn(SingletonComponent::class)
object JwtDatabaseModule {

    /**
     * Provides an instance of [JwtTokenRepository].
     *
     * This method creates and provides a singleton instance of [JwtTokenRepository] using the provided
     * [AppDatabase] instance. The [JwtTokenRepository] will use the [JwtTokenDao] from the database to perform
     * database operations related to JWT tokens.
     *
     * @param appDatabase An instance of [AppDatabase] used to access the [JwtTokenDao].
     * @return A singleton instance of [JwtTokenRepository] for managing JWT token data.
     */
    @Provides
    @Singleton
    fun provideJwtTokenRepository(
        appDatabase: AppDatabase
    ): JwtTokenRepository {
        return JwtTokenRepository(appDatabase.jwtTokenDao())
    }
}
