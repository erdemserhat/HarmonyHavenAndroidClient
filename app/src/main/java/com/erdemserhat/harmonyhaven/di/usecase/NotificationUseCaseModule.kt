package com.erdemserhat.harmonyhaven.di.usecase

import com.erdemserhat.harmonyhaven.data.api.notification.NotificationApiService
import com.erdemserhat.harmonyhaven.domain.usecase.notification.GetNotification
import com.erdemserhat.harmonyhaven.domain.usecase.notification.NotificationUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module for providing use cases related to notifications.
 *
 * This module is responsible for providing an instance of [NotificationUseCases], which aggregates
 * various use case classes for handling notification-related operations. It uses Dagger Hilt for dependency
 * injection to ensure that these use cases are available as singletons throughout the application.
 */
@Module
@InstallIn(SingletonComponent::class)
object NotificationUseCaseModule {

    /**
     * Provides a singleton instance of [NotificationUseCases].
     *
     * This method creates an instance of [NotificationUseCases] by injecting the necessary use case classes.
     * It also provides the [NotificationApiService] required for making network requests related to notifications.
     *
     * @param notificationApiService An instance of [NotificationApiService] used for notification-related API operations.
     * @return A singleton instance of [NotificationUseCases] that aggregates all notification-related use cases.
     */
    @Provides
    @Singleton
    fun provideNotificationUseCase(
        notificationApiService: NotificationApiService
    ): NotificationUseCases {
        return NotificationUseCases(
            getNotification = GetNotification(notificationApiService)
        )
    }
}
