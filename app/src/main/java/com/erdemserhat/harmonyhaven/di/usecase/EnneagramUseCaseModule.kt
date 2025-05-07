package com.erdemserhat.harmonyhaven.di.usecase

import com.erdemserhat.harmonyhaven.data.api.enneagram.EnneagramApiService
import com.erdemserhat.harmonyhaven.data.api.notification.NotificationApiService
import com.erdemserhat.harmonyhaven.domain.usecase.EnneagramUseCase
import com.erdemserhat.harmonyhaven.domain.usecase.notification.GetNotification
import com.erdemserhat.harmonyhaven.domain.usecase.notification.NotificationUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EnneagramUseCaseModule {

    @Provides
    @Singleton
    fun provideEnneagramUseCase(
        enneagramApiService: EnneagramApiService
    ): EnneagramUseCase {
        return EnneagramUseCase(
            enneagramApiService = enneagramApiService
        )
    }
}