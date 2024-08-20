package com.erdemserhat.harmonyhaven.data.api.article

import com.erdemserhat.harmonyhaven.domain.model.rest.Article
import com.erdemserhat.harmonyhaven.domain.model.rest.ArticleResponseType
import com.erdemserhat.harmonyhaven.domain.model.rest.Category
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ArticleApiService {

    // Fetches a list of categories from the server.
    // The server response is wrapped in a Retrofit Response object,
    // allowing us to check if the request was successful or not.
    @GET("categories")
    suspend fun getCategories(): Response<List<Category>>

    // Fetches a list of articles from the server.
    // The server response is wrapped in a Retrofit Response object.
    @GET("articles")
    suspend fun getArticles(): Response<List<Article>>

    // Fetches a specific article based on the provided article ID.
    // The article ID is passed as a path parameter in the URL.
    // The server response is wrapped in a Retrofit Response object.
    // The response type is `ArticleResponseType` to match the expected structure from the server.
    @GET("articles/{id}")
    suspend fun getArticleById(
        @Path("id") id: Int // The ID of the article to fetch
    ): Response<ArticleResponseType>

}