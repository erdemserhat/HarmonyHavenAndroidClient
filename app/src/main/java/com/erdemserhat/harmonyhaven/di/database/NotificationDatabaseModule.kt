package com.erdemserhat.harmonyhaven.di.database

import com.erdemserhat.harmonyhaven.data.local.AppDatabase
import com.erdemserhat.harmonyhaven.data.local.repository.NotificationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * Dagger Hilt module for providing dependencies related to notification management.
 *
 * This module provides instances of repository classes that interact with the notification database.
 * It is installed in the singleton component, ensuring that the provided dependencies are singletons
 * throughout the application's lifecycle.
 */
@Module
@InstallIn(SingletonComponent::class)
object NotificationDatabaseModule {

    /**
     * Provides an instance of [NotificationRepository].
     *
     * This method creates and provides a singleton instance of [NotificationRepository] using the provided
     * [AppDatabase] instance. The [NotificationRepository] will use the [NotificationDao] from the database to perform
     * database operations related to notifications.
     *
     * @param appDatabase An instance of [AppDatabase] used to access the [NotificationDao].
     * @return A singleton instance of [NotificationRepository] for managing notification data.
     */
    @Provides
    @Singleton
    fun provideNotificationRepository(
        appDatabase: AppDatabase
    ): NotificationRepository {
        return NotificationRepository(appDatabase.notificationDao())
    }
}
