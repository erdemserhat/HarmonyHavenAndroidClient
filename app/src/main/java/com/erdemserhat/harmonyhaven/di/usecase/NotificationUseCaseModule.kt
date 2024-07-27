package com.erdemserhat.harmonyhaven.di.usecase

import com.erdemserhat.harmonyhaven.data.api.notification.NotificationApiService
import com.erdemserhat.harmonyhaven.domain.usecase.notification.GetNotification
import com.erdemserhat.harmonyhaven.domain.usecase.notification.NotificationUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationUseCaseModule {


    @Provides
    @Singleton
    fun provideNotificationUseCase(
        notificationApiService: NotificationApiService
    ):NotificationUseCases{
        return NotificationUseCases(
            getNotification = GetNotification(notificationApiService)
        )
    }

}