package com.erdemserhat.harmonyhaven.di.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 * Dagger Hilt module for providing network-related dependencies including OkHttpClient and Retrofit.
 *
 * This module is responsible for providing instances of [OkHttpClient] and [Retrofit] that are
 * configured for network operations. It uses Dagger Hilt for dependency injection and ensures
 * that these network components are available as singletons throughout the application.
 */
@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    /**
     * Provides a singleton instance of [OkHttpClient].
     *
     * This method creates an instance of [OkHttpClient] with specific configurations, including
     * timeouts and interceptors. The [JwtInterceptor] is added to handle JWT authentication for
     * network requests.
     *
     * @param jwtInterceptor An instance of [JwtInterceptor] for adding JWT tokens to requests.
     * @return A singleton instance of [OkHttpClient] configured with timeouts and interceptors.
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(
        jwtInterceptor: JwtInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES) // Sets the timeout for connecting to the server
            .readTimeout(45, TimeUnit.SECONDS) // Sets the timeout for reading data from the server
            .writeTimeout(30, TimeUnit.SECONDS) // Sets the timeout for writing data to the server
            .addInterceptor(jwtInterceptor) // Adds the JWT interceptor for authentication
            .build()
    }

    /**
     * Provides a singleton instance of [Retrofit].
     *
     * This method creates an instance of [Retrofit] configured with the base URL and the [OkHttpClient].
     * It also adds a [GsonConverterFactory] for JSON serialization and deserialization.
     *
     * @param client An instance of [OkHttpClient] used for network operations.
     * @return A singleton instance of [Retrofit] configured with the provided client and base URL.
     */
    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NetworkConstants.BASE_URL) // Base URL for API requests
            .client(client) // Sets the OkHttpClient instance
            .addConverterFactory(GsonConverterFactory.create()) // Adds Gson for JSON conversion
            .build()
    }
}
