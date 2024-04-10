package com.erdemserhat.harmonyhaven.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.erdemserhat.harmonyhaven.data.network.CategoryApiService
import com.erdemserhat.harmonyhaven.data.network.FcmApiService
import com.erdemserhat.harmonyhaven.data.network.PasswordResetApiService
import com.erdemserhat.harmonyhaven.data.network.UserApiService
import com.erdemserhat.harmonyhaven.data.network.UserAuthenticationApiService
import com.erdemserhat.harmonyhaven.data.network.UserRegistrationApiService
import com.erdemserhat.harmonyhaven.data.room.AppDatabase
import com.erdemserhat.harmonyhaven.data.room.JwtTokenRepository
import com.erdemserhat.harmonyhaven.domain.usecase.article.ArticleUseCases
import com.erdemserhat.harmonyhaven.domain.usecase.article.Categories
import com.erdemserhat.harmonyhaven.domain.usecase.article.GetAllArticles
import com.erdemserhat.harmonyhaven.domain.usecase.article.GetArticleById
import com.erdemserhat.harmonyhaven.domain.usecase.article.GetArticlesByCategory
import com.erdemserhat.harmonyhaven.domain.usecase.article.GetRecentArticles
import com.erdemserhat.harmonyhaven.domain.usecase.users.AuthenticateUser
import com.erdemserhat.harmonyhaven.domain.usecase.users.DeleteUser
import com.erdemserhat.harmonyhaven.domain.usecase.users.FcmEnrolment
import com.erdemserhat.harmonyhaven.domain.usecase.users.LoginUser
import com.erdemserhat.harmonyhaven.domain.usecase.users.RegisterUser
import com.erdemserhat.harmonyhaven.domain.usecase.users.ResetPasswordUserDev
import com.erdemserhat.harmonyhaven.domain.usecase.users.UpdateUser
import com.erdemserhat.harmonyhaven.domain.usecase.users.UserUseCases
import com.erdemserhat.harmonyhaven.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideUserApiService(retrofit: Retrofit): UserApiService {
        return retrofit.create(UserApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideFcmApiService(retrofit: Retrofit): FcmApiService {
        return retrofit.create(FcmApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCategoryApiService(retrofit: Retrofit): CategoryApiService {
        return retrofit.create(CategoryApiService::class.java)
    }
    
    @Provides
    @Singleton
    fun provideUserAuthenticationApiService(retrofit: Retrofit):UserAuthenticationApiService{
        return retrofit.create(UserAuthenticationApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRegistrationApiService(retrofit: Retrofit):UserRegistrationApiService{
        return retrofit.create(UserRegistrationApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideResetPasswordMailerApiService(retrofit: Retrofit):PasswordResetApiService{
        return retrofit.create(PasswordResetApiService::class.java)
    }



    @Provides
    @Singleton
    fun provideUserUseCases(
        userApiService: UserApiService,
        fcmApiService: FcmApiService,
        userAuthApiService:UserAuthenticationApiService,
        userRegistrationApiService: UserRegistrationApiService
    ):UserUseCases{
        return UserUseCases(
            loginUser = LoginUser(userApiService),
            registerUser = RegisterUser(userRegistrationApiService),
            updateUser = UpdateUser(userApiService),
            deleteUSer = DeleteUser(userApiService),
            resetPasswordUser = ResetPasswordUserDev(userApiService),
            authenticateUser = AuthenticateUser(userAuthApiService),
            fcmEnrolment = FcmEnrolment(fcmApiService)
        )


    }

    @Provides
    @Singleton
    fun provideArticleUseCases(categoryApiService: CategoryApiService):ArticleUseCases{
        return ArticleUseCases(
            Categories(categoryApiService),
            GetArticleById(categoryApiService),
            GetRecentArticles(categoryApiService),
            GetArticlesByCategory(categoryApiService),
            GetAllArticles(categoryApiService)
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
