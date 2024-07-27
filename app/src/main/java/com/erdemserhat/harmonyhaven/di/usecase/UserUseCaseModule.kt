package com.erdemserhat.harmonyhaven.di.usecase

import com.erdemserhat.harmonyhaven.data.api.fcm.FcmApiService
import com.erdemserhat.harmonyhaven.data.api.user.AuthenticationStatusCheckerApiService
import com.erdemserhat.harmonyhaven.data.api.user.InformationUpdateApiService
import com.erdemserhat.harmonyhaven.data.api.user.PasswordResetApiService
import com.erdemserhat.harmonyhaven.data.api.user.UserAuthenticationApiService
import com.erdemserhat.harmonyhaven.data.api.user.UserInformationApiService
import com.erdemserhat.harmonyhaven.data.api.user.UserRegistrationApiService
import com.erdemserhat.harmonyhaven.domain.usecase.password_reset.AuthenticatePasswordResetAttempt
import com.erdemserhat.harmonyhaven.domain.usecase.password_reset.CompletePasswordResetAttempt
import com.erdemserhat.harmonyhaven.domain.usecase.password_reset.SendPasswordResetMail
import com.erdemserhat.harmonyhaven.domain.usecase.user.AuthenticateUser
import com.erdemserhat.harmonyhaven.domain.usecase.user.CheckUserAuthenticationStatus
import com.erdemserhat.harmonyhaven.domain.usecase.user.FcmEnrolment
import com.erdemserhat.harmonyhaven.domain.usecase.user.GetUserInformation
import com.erdemserhat.harmonyhaven.domain.usecase.user.RegisterUser
import com.erdemserhat.harmonyhaven.domain.usecase.user.UpdateUserInformation
import com.erdemserhat.harmonyhaven.domain.usecase.user.UserUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserUseCaseModule {
    @Provides
    @Singleton
    fun provideUserUseCases(
        fcmApiService: FcmApiService,
        userAuthApiService: UserAuthenticationApiService,
        userRegistrationApiService: UserRegistrationApiService,
        passwordResetApiService: PasswordResetApiService,
        authenticationStatusCheckerApiService: AuthenticationStatusCheckerApiService,
        getUserInformationApiService: UserInformationApiService,
        updateApiService: InformationUpdateApiService
    ): UserUseCases {
        return UserUseCases(
            registerUser = RegisterUser(userRegistrationApiService),
            authenticateUser = AuthenticateUser(userAuthApiService),
            fcmEnrolment = FcmEnrolment(fcmApiService),
            sendPasswordResetMail = SendPasswordResetMail(passwordResetApiService),
            authenticatePasswordResetAttempt = AuthenticatePasswordResetAttempt(passwordResetApiService),
            completePasswordResetAttempt = CompletePasswordResetAttempt(passwordResetApiService),
            checkUserAuthenticationStatus = CheckUserAuthenticationStatus(authenticationStatusCheckerApiService),
            getUserInformation = GetUserInformation(getUserInformationApiService),
            updateUserInformation = UpdateUserInformation(updateApiService)
        )


    }
}