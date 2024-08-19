package com.erdemserhat.harmonyhaven.di.network

import com.erdemserhat.harmonyhaven.data.api.notification.NotificationApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Dagger Hilt module for providing network-related dependencies related to notifications.
 *
 * This module is used to provide a singleton instance of the [NotificationApiService] that can be
 * injected wherever needed in the application. It uses Dagger Hilt for dependency injection.
 */
@Module
@InstallIn(SingletonComponent::class)
object NotificationNetworkModule {

    /**
     * Provides a singleton instance of [NotificationApiService].
     *
     * This method creates an instance of [NotificationApiService] using the provided [Retrofit] instance.
     * The [NotificationApiService] is used to make network requests related to notifications, such as
     * fetching or managing notifications from the server.
     *
     * @param retrofit The [Retrofit] instance used to create the [NotificationApiService].
     * @return A singleton instance of [NotificationApiService].
     */
    @Provides
    @Singleton
    fun provideNotificationApiService(retrofit: Retrofit): NotificationApiService {
        return retrofit.create(NotificationApiService::class.java)
    }
}
