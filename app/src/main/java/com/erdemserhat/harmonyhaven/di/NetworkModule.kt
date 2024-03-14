package com.erdemserhat.harmonyhaven.di

import com.erdemserhat.harmonyhaven.data.network.UserApiService
import com.erdemserhat.harmonyhaven.domain.usecase.users.DeleteUser
import com.erdemserhat.harmonyhaven.domain.usecase.users.LoginUser
import com.erdemserhat.harmonyhaven.domain.usecase.users.RegisterUser
import com.erdemserhat.harmonyhaven.domain.usecase.users.ResetPasswordUser
import com.erdemserhat.harmonyhaven.domain.usecase.users.ResetPasswordUserDev
import com.erdemserhat.harmonyhaven.domain.usecase.users.UpdateUser
import com.erdemserhat.harmonyhaven.domain.usecase.users.UserUseCases
import com.erdemserhat.harmonyhaven.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    fun provideHttpClient():OkHttpClient{
        return OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(45, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }



    @Provides
    @Singleton
    fun provideRetrofit(
        client:OkHttpClient
    ):Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton

    fun provideUserApiService(retrofit: Retrofit): UserApiService {
        return retrofit.create(UserApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserUseCases(userApiService: UserApiService):UserUseCases{
        return UserUseCases(
            loginUser = LoginUser(userApiService),
            registerUser = RegisterUser(userApiService),
            updateUser = UpdateUser(userApiService),
            deleteUSer = DeleteUser(userApiService),
            resetPasswordUser = ResetPasswordUserDev(userApiService)
        )


    }

}