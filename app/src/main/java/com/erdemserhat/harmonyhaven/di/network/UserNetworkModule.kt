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
/**
 * Dagger Hilt module for providing user-related network API services.
 *
 * This module is responsible for providing instances of various API services related to user
 * authentication, registration, password reset, and user information updates. It uses Dagger Hilt
 * for dependency injection to ensure these services are available as singletons throughout the application.
 */
@Module
@InstallIn(SingletonComponent::class)
object UserNetworkModule {

    /**
     * Provides a singleton instance of [UserAuthenticationApiService].
     *
     * This method creates an instance of [UserAuthenticationApiService] using Retrofit for user
     * authentication API operations.
     *
     * @param retrofit An instance of [Retrofit] used for creating the API service.
     * @return A singleton instance of [UserAuthenticationApiService] for authentication operations.
     */
    @Provides
    @Singleton
    fun provideUserAuthenticationApiService(retrofit: Retrofit): UserAuthenticationApiService {
        return retrofit.create(UserAuthenticationApiService::class.java)
    }

    /**
     * Provides a singleton instance of [UserRegistrationApiService].
     *
     * This method creates an instance of [UserRegistrationApiService] using Retrofit for user
     * registration API operations.
     *
     * @param retrofit An instance of [Retrofit] used for creating the API service.
     * @return A singleton instance of [UserRegistrationApiService] for registration operations.
     */
    @Provides
    @Singleton
    fun provideUserRegistrationApiService(retrofit: Retrofit): UserRegistrationApiService {
        return retrofit.create(UserRegistrationApiService::class.java)
    }

    /**
     * Provides a singleton instance of [PasswordResetApiService].
     *
     * This method creates an instance of [PasswordResetApiService] using Retrofit for password
     * reset API operations.
     *
     * @param retrofit An instance of [Retrofit] used for creating the API service.
     * @return A singleton instance of [PasswordResetApiService] for password reset operations.
     */
    @Provides
    @Singleton
    fun provideResetPasswordApiService(retrofit: Retrofit): PasswordResetApiService {
        return retrofit.create(PasswordResetApiService::class.java)
    }

    /**
     * Provides a singleton instance of [AuthenticationStatusCheckerApiService].
     *
     * This method creates an instance of [AuthenticationStatusCheckerApiService] using Retrofit
     * to check the authentication status of the user.
     *
     * @param retrofit An instance of [Retrofit] used for creating the API service.
     * @return A singleton instance of [AuthenticationStatusCheckerApiService] for checking authentication status.
     */
    @Provides
    @Singleton
    fun provideAuthenticationStatusCheckerApiService(retrofit: Retrofit): AuthenticationStatusCheckerApiService {
        return retrofit.create(AuthenticationStatusCheckerApiService::class.java)
    }

    /**
     * Provides a singleton instance of [UserInformationApiService].
     *
     * This method creates an instance of [UserInformationApiService] using Retrofit to fetch user
     * information from the API.
     *
     * @param retrofit An instance of [Retrofit] used for creating the API service.
     * @return A singleton instance of [UserInformationApiService] for retrieving user information.
     */
    @Provides
    @Singleton
    fun provideUserInformationApiService(retrofit: Retrofit): UserInformationApiService {
        return retrofit.create(UserInformationApiService::class.java)
    }

    /**
     * Provides a singleton instance of [InformationUpdateApiService].
     *
     * This method creates an instance of [InformationUpdateApiService] using Retrofit for updating
     * user information such as password and name.
     *
     * @param retrofit An instance of [Retrofit] used for creating the API service.
     * @return A singleton instance of [InformationUpdateApiService] for updating user information.
     */
    @Provides
    @Singleton
    fun provideUserInformationUpdateApiService(retrofit: Retrofit): InformationUpdateApiService {
        return retrofit.create(InformationUpdateApiService::class.java)
    }
}
