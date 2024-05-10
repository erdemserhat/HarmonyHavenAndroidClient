package com.erdemserhat.harmonyhaven.di.network

import com.erdemserhat.harmonyhaven.data.api.fcm.FcmApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FcmNetworkModule {

    @Provides
    @Singleton
    fun provideFcmApiService(retrofit: Retrofit): FcmApiService {
        return retrofit.create(FcmApiService::class.java)
    }

}