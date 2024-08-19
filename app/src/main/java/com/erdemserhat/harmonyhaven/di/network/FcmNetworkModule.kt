package com.erdemserhat.harmonyhaven.di.network

import com.erdemserhat.harmonyhaven.data.api.fcm.FcmApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


/**
 * Dagger Hilt module for providing network-related dependencies for Firebase Cloud Messaging (FCM).
 *
 * This module provides an instance of the [FcmApiService] for making API requests related to Firebase Cloud Messaging.
 * It is installed in the singleton component, ensuring that the provided dependencies are singletons
 * throughout the application's lifecycle.
 */
@Module
@InstallIn(SingletonComponent::class)
object FcmNetworkModule {

    /**
     * Provides an instance of [FcmApiService].
     *
     * This method creates and provides a singleton instance of [FcmApiService] using the provided [Retrofit] instance.
     * The [FcmApiService] is used for making network requests related to FCM, such as updating FCM registration IDs.
     *
     * @param retrofit An instance of [Retrofit] used to create the [FcmApiService] instance.
     * @return A singleton instance of [FcmApiService] for interacting with FCM-related API endpoints.
     */
    @Provides
    @Singleton
    fun provideFcmApiService(retrofit: Retrofit): FcmApiService {
        return retrofit.create(FcmApiService::class.java)
    }
}
