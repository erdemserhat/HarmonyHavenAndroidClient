package com.erdemserhat.harmonyhaven.di.network

import com.erdemserhat.harmonyhaven.data.api.user.AuthenticationStatusCheckerApiService
import com.erdemserhat.harmonyhaven.data.api.user.InformationUpdateApiService
import com.erdemserhat.harmonyhaven.data.api.user.PasswordResetApiService
import com.erdemserhat.harmonyhaven.data.api.user.UserAuthenticationApiService
import com.erdemserhat.harmonyhaven.data.api.user.UserInformationApiService
import com.erdemserhat.harmonyhaven.data.api.user.UserRegistrationApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object UserNetworkModule {
    @Provides
    @Singleton
    fun provideUserAuthenticationApiService(retrofit: Retrofit): UserAuthenticationApiService {
        return retrofit.create(UserAuthenticationApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRegistrationApiService(retrofit: Retrofit): UserRegistrationApiService {
        return retrofit.create(UserRegistrationApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideResetPasswordApiService(retrofit: Retrofit): PasswordResetApiService {
        return retrofit.create(PasswordResetApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthenticationStatusCheckerApiService(retrofit: Retrofit): AuthenticationStatusCheckerApiService {
        return retrofit.create(AuthenticationStatusCheckerApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserInformationApiService(retrofit: Retrofit): UserInformationApiService {
        return retrofit.create(UserInformationApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserInformationUpdateApiService(retrofit: Retrofit): InformationUpdateApiService {
        return retrofit.create(InformationUpdateApiService::class.java)
    }


}