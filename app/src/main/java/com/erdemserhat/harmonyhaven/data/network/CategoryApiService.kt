package com.erdemserhat.harmonyhaven.data.network

import com.erdemserhat.harmonyhaven.domain.model.rest.Article
import com.erdemserhat.harmonyhaven.domain.model.rest.ArticleResponseType
import com.erdemserhat.harmonyhaven.domain.model.rest.Category
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoryApiService {
    @GET("categories")
    suspend fun getAllCategories():List<Category>

    @GET("articles")
    suspend fun getAllArticles():List<Article>

    @GET("articles/recent/{size}")
    suspend fun getRecentArticles(@Path("size") size:Int):Response<List<ArticleResponseType>>

    @GET("articles/{id}")
    suspend fun getArticleById(@Path("id") id: Int): Response<ArticleResponseType>

    @GET("articles/category/{id}")
    suspend fun getArticlesByCategory(@Path("id") id :Int) :Response<List<ArticleResponseType>>





}