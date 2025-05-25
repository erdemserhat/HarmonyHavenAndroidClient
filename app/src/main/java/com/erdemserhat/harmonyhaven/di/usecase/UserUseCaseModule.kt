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
import com.erdemserhat.harmonyhaven.domain.usecase.user.AuthenticateUserViaGoogle
import com.erdemserhat.harmonyhaven.domain.usecase.user.CheckUserAuthenticationStatus
import com.erdemserhat.harmonyhaven.domain.usecase.user.FcmEnrolment
import com.erdemserhat.harmonyhaven.domain.usecase.user.GetAllMoods
import com.erdemserhat.harmonyhaven.domain.usecase.user.GetUserInformation
import com.erdemserhat.harmonyhaven.domain.usecase.user.GetUserMood
import com.erdemserhat.harmonyhaven.domain.usecase.user.RegisterUser
import com.erdemserhat.harmonyhaven.domain.usecase.user.UpdateUserInformation
import com.erdemserhat.harmonyhaven.domain.usecase.user.UpdateUserMood
import com.erdemserhat.harmonyhaven.domain.usecase.user.UserUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module for providing use cases related to user operations.
 *
 * This module is responsible for providing an instance of [UserUseCases], which aggregates various use cases
 * related to user management and authentication. It uses Dagger Hilt for dependency injection to ensure
 * that the use cases are available as singletons throughout the application.
 */
@Module
@InstallIn(SingletonComponent::class)
object UserUseCaseModule {

    /**
     * Provides a singleton instance of [UserUseCases].
     *
     * This method creates an instance of [UserUseCases] by injecting the necessary API services required
     * for user-related operations, including authentication, registration, password reset, and user information management.
     *
     * @param fcmApiService An instance of [FcmApiService] used for Firebase Cloud Messaging operations.
     * @param userAuthApiService An instance of [UserAuthenticationApiService] used for user authentication.
     * @param userRegistrationApiService An instance of [UserRegistrationApiService] used for user registration.
     * @param passwordResetApiService An instance of [PasswordResetApiService] used for password reset operations.
     * @param authenticationStatusCheckerApiService An instance of [AuthenticationStatusCheckerApiService] used for checking user authentication status.
     * @param getUserInformationApiService An instance of [UserInformationApiService] used for retrieving user information.
     * @param updateApiService An instance of [InformationUpdateApiService] used for updating user information.
     * @return A singleton instance of [UserUseCases] containing various user-related use cases.
     */
    @Provides
    @Singleton
    fun provideUserUseCases(
        fcmApiService: FcmApiService,
        userAuthApiService: UserAuthenticationApiService,
        userRegistrationApiService: UserRegistrationApiService,
        passwordResetApiService: PasswordResetApiService,
        authenticationStatusCheckerApiService: AuthenticationStatusCheckerApiService,
        getUserInformationApiService: UserInformationApiService,
        updateApiService: InformationUpdateApiService,
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
            updateUserInformation = UpdateUserInformation(updateApiService),
            authenticateUserViaGoogle = AuthenticateUserViaGoogle(userAuthApiService),
            getUserMood = GetUserMood(getUserInformationApiService),
            updateUserMood = UpdateUserMood(getUserInformationApiService),
            getAllMoods = GetAllMoods(getUserInformationApiService)
        )
    }
}
