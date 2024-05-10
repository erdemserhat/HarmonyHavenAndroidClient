package com.erdemserhat.harmonyhaven.data.api.article

import com.erdemserhat.harmonyhaven.domain.model.rest.Article
import com.erdemserhat.harmonyhaven.domain.model.rest.Category
import com.erdemserhat.harmonyhaven.dto.requests.FcmSetupRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

interface ArticleApiService {

    @GET("categories")
    suspend fun getCategories(): Response<List<Category>>

    @GET("articles")
    suspend fun getArticles():Response<List<Article>>
}