package com.erdemserhat.harmonyhaven.di

import com.erdemserhat.harmonyhaven.data.api.VersionCheckingApiService
import com.erdemserhat.harmonyhaven.data.api.fcm.FcmApiService
import com.erdemserhat.harmonyhaven.domain.usecase.VersionControlUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VersionModule {

    @Provides
    @Singleton
    fun provideVersionCheckModule(retrofit: Retrofit): VersionCheckingApiService {
        return retrofit.create(VersionCheckingApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideVersionControlUseCase(versionCheckingApiService: VersionCheckingApiService): VersionControlUseCase {
        return VersionControlUseCase(versionCheckingApiService)

    }
}