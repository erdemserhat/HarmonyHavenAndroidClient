package com.erdemserhat.harmonyhaven.di.database

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.erdemserhat.harmonyhaven.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * Dagger Hilt module for providing Room database-related dependencies.
 *
 * This module provides instances of the Room database and the application context.
 * It is installed in the singleton component, ensuring that the provided dependencies are singletons
 * throughout the application's lifecycle.
 */
@Module
@InstallIn(SingletonComponent::class)
object RoomDatabaseModule {

    /**
     * Provides the application context.
     *
     * This method provides the application context as a singleton instance, which is necessary for initializing
     * the Room database. The application context is used to ensure that the database instance is not tied
     * to any specific activity or component, making it suitable for long-lived operations.
     *
     * @param application An instance of [Application] used to get the application context.
     * @return The application context as a [Context] instance.
     */
    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    /**
     * Provides an instance of [AppDatabase].
     *
     * This method creates and provides a singleton instance of [AppDatabase] using the provided application
     * context. The Room database is built with a fallback strategy to destructive migration, meaning that
     * if the schema is not compatible with the existing database, the old database will be destroyed and
     * recreated with the new schema.
     *
     * @param context The application context used to initialize the Room database.
     * @return A singleton instance of [AppDatabase] for managing database operations.
     */
    @Provides
    @Singleton
    fun provideRoomDatabase(
        context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext, // Use applicationContext to avoid leaking activities or other components
            AppDatabase::class.java,
            "harmony-haven-database" // Database name
        )
            .addMigrations(AppDatabase.MIGRATION_5_6,AppDatabase.MIGRATION_6_7) // Add migrations
            .build()
    }

}
