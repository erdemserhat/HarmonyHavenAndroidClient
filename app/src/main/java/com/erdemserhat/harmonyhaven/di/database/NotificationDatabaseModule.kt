package com.erdemserhat.harmonyhaven.di.database

import com.erdemserhat.harmonyhaven.data.local.AppDatabase
import com.erdemserhat.harmonyhaven.data.local.repository.NotificationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NotificationDatabaseModule {
    @Provides
    @Singleton
    fun provideNotificationRepository(
        appDatabase: AppDatabase
    ): NotificationRepository {
        return NotificationRepository(appDatabase.notificationDao())
    }


}