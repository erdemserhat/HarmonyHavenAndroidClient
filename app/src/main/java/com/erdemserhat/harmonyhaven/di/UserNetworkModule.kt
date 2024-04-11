package com.erdemserhat.harmonyhaven.di

import com.erdemserhat.harmonyhaven.data.api.fcm.FcmApiService
import com.erdemserhat.harmonyhaven.data.api.user.PasswordResetApiService
import com.erdemserhat.harmonyhaven.data.api.user.UserAuthenticationApiService
import com.erdemserhat.harmonyhaven.data.api.user.UserRegistrationApiService
import com.erdemserhat.harmonyhaven.domain.usecase.password_reset.AuthenticatePasswordResetAttempt
import com.erdemserhat.harmonyhaven.domain.usecase.password_reset.CompletePasswordResetAttempt
import com.erdemserhat.harmonyhaven.domain.usecase.password_reset.SendPasswordResetMail
import com.erdemserhat.harmonyhaven.domain.usecase.users.AuthenticateUser
import com.erdemserhat.harmonyhaven.domain.usecase.users.FcmEnrolment
import com.erdemserhat.harmonyhaven.domain.usecase.users.RegisterUser
import com.erdemserhat.harmonyhaven.domain.usecase.users.UserUseCases
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
    fun provideUserUseCases(
        fcmApiService: FcmApiService,
        userAuthApiService: UserAuthenticationApiService,
        userRegistrationApiService: UserRegistrationApiService,
        passwordResetApiService: PasswordResetApiService
    ): UserUseCases {
        return UserUseCases(
            registerUser = RegisterUser(userRegistrationApiService),
            authenticateUser = AuthenticateUser(userAuthApiService),
            fcmEnrolment = FcmEnrolment(fcmApiService),
            sendPasswordResetMail = SendPasswordResetMail(passwordResetApiService),
            authenticatePasswordResetAttempt = AuthenticatePasswordResetAttempt(passwordResetApiService),
            completePasswordResetAttempt = CompletePasswordResetAttempt(passwordResetApiService)
        )


    }
}