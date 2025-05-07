package com.erdemserhat.harmonyhaven.di.network

import com.erdemserhat.harmonyhaven.data.api.enneagram.EnneagramApiService
import com.erdemserhat.harmonyhaven.data.api.fcm.FcmApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EnneagramNetworkModule {

    @Provides
    @Singleton
    fun provideEnneagramApiService(retrofit: Retrofit): EnneagramApiService {
        return retrofit.create(EnneagramApiService::class.java)
    }
}