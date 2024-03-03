package com.erdemserhat.harmonyhaven.di

import com.erdemserhat.harmonyhaven.network.UserApiService
import com.erdemserhat.harmonyhaven.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {



    @Provides
    @Singleton
    fun provideRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton

    fun provideUserApiService(retrofit: Retrofit):UserApiService{
        return retrofit.create(UserApiService::class.java)
    }

}