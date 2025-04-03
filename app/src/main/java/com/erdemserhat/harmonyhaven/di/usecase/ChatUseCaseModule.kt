package com.erdemserhat.harmonyhaven.di.usecase

import com.erdemserhat.harmonyhaven.data.api.ChatApiService
import com.erdemserhat.harmonyhaven.data.api.SSEClient
import com.erdemserhat.harmonyhaven.data.api.notification.NotificationApiService
import com.erdemserhat.harmonyhaven.domain.usecase.ChatUseCase
import com.erdemserhat.harmonyhaven.domain.usecase.notification.GetNotification
import com.erdemserhat.harmonyhaven.domain.usecase.notification.NotificationUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ChatUseCaseModule {

    @Provides
    @Singleton
    fun provideChatUseCase(
        chatApiService: ChatApiService
    ): ChatUseCase {

        return ChatUseCase(
            sseClient = SSEClient()
        )
    }
}