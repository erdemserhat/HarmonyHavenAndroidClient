package com.erdemserhat.harmonyhaven.di.network

import android.content.Context
import com.erdemserhat.harmonyhaven.BuildConfig
import com.erdemserhat.harmonyhaven.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.ConnectionPool
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InputStream
import java.security.KeyStore
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager


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

    @Provides
    @Singleton
    fun provideOkHttpClient(
        jwtInterceptor: JwtInterceptor,
        context: Context
    ): OkHttpClient {

        // Configure CertificatePinner with the SHA-256 fingerprint
        val certificatePinner = CertificatePinner.Builder()
            .add(BuildConfig.SERVER_MAIN_PATTERN, BuildConfig.SSL_CERTIFICATE) // Use the updated fingerprint
            .build()
        // Build and return the OkHttpClient
        return OkHttpClient.Builder()
            .dispatcher(Dispatcher().apply {
                maxRequestsPerHost = 10
            })
            .certificatePinner(certificatePinner)
            .connectTimeout(1, TimeUnit.MINUTES) // Connection timeout
            .readTimeout(45, TimeUnit.SECONDS) // Read timeout
            .writeTimeout(30, TimeUnit.SECONDS) // Write timeout
            .addInterceptor(jwtInterceptor) // Add JWT interceptor for authentication
            .build()
    }



    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL) // Base URL for API requests
            .client(client) // Sets the OkHttpClient instance
            .addConverterFactory(GsonConverterFactory.create()) // Adds Gson for JSON conversion
            .build()
    }
}
