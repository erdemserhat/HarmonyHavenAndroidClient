package com.erdemserhat.harmonyhaven.domain.usecase.article

import com.erdemserhat.harmonyhaven.data.api.article.ArticleApiService
import com.erdemserhat.harmonyhaven.domain.model.rest.Category
import javax.inject.Inject

class GetCategories @Inject constructor(
    private val articleApiService: ArticleApiService
) {
    suspend fun executeRequest(): List<Category>? {
        try {
            val response = articleApiService.getCategories()
            if (response.isSuccessful)
                return response.body() ?: listOf()
            else return null

        } catch (e: Exception) {
            return null

        }
    }
}