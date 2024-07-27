package com.erdemserhat.harmonyhaven.di.network

import com.erdemserhat.harmonyhaven.data.api.fcm.FcmApiService
import com.erdemserhat.harmonyhaven.data.api.notification.NotificationApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationNetworkModule {

    @Provides
    @Singleton
    fun provideNotificationApiService(retrofit: Retrofit): NotificationApiService {
        return retrofit.create(NotificationApiService::class.java)
    }
}