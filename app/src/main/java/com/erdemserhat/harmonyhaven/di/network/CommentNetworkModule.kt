package com.erdemserhat.harmonyhaven.di.network

import com.erdemserhat.harmonyhaven.data.api.article.ArticleApiService
import com.erdemserhat.harmonyhaven.data.api.comment.CommentApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommentNetworkModule {

    @Provides
    @Singleton
    fun provideCommentNetworkModule(retrofit: Retrofit): CommentApiService {
        return retrofit.create(CommentApiService::class.java)
    }
}