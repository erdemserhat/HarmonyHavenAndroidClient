package com.erdemserhat.harmonyhaven.di

import com.erdemserhat.harmonyhaven.data.api.fcm.FcmApiService
import com.erdemserhat.harmonyhaven.data.api.user.PasswordResetApiService
import com.erdemserhat.harmonyhaven.data.api.user.UserAuthenticationApiService
import com.erdemserhat.harmonyhaven.data.api.user.UserRegistrationApiService
import com.erdemserhat.harmonyhaven.data.local.AppDatabase
import com.erdemserhat.harmonyhaven.data.local.repository.JwtTokenRepository
import com.erdemserhat.harmonyhaven.domain.usecase.article.ArticleUseCases
import com.erdemserhat.harmonyhaven.domain.usecase.article.Categories
import com.erdemserhat.harmonyhaven.domain.usecase.article.GetAllArticles
import com.erdemserhat.harmonyhaven.domain.usecase.article.GetArticleById
import com.erdemserhat.harmonyhaven.domain.usecase.article.GetArticlesByCategory
import com.erdemserhat.harmonyhaven.domain.usecase.article.GetRecentArticles
import com.erdemserhat.harmonyhaven.domain.usecase.password_reset.AuthenticatePasswordResetAttempt
import com.erdemserhat.harmonyhaven.domain.usecase.users.AuthenticateUser
import com.erdemserhat.harmonyhaven.domain.usecase.password_reset.CompletePasswordResetAttempt
import com.erdemserhat.harmonyhaven.domain.usecase.users.FcmEnrolment
import com.erdemserhat.harmonyhaven.domain.usecase.users.RegisterUser
import com.erdemserhat.harmonyhaven.domain.usecase.password_reset.SendPasswordResetMail
import com.erdemserhat.harmonyhaven.domain.usecase.users.UserUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    fun provideFcmApiService(retrofit: Retrofit): FcmApiService {
        return retrofit.create(FcmApiService::class.java)
    }




    //Todo("Implement the logic")
    @Provides
    @Singleton
    fun provideArticleUseCases():ArticleUseCases{
        return ArticleUseCases(
            Categories(),
            GetArticleById(),
            GetRecentArticles(),
            GetArticlesByCategory(),
            GetAllArticles()
        )
    }



    @Provides
    @Singleton
    fun provideJwtTokenRepository(
        appDatabase: AppDatabase
    ): JwtTokenRepository {
        return JwtTokenRepository(appDatabase.jwtTokenDao())
    }





}
