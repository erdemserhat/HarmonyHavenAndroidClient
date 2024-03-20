package com.erdemserhat.harmonyhaven.domain.usecase.article

import com.erdemserhat.harmonyhaven.data.network.CategoryApiService
import com.erdemserhat.harmonyhaven.domain.model.rest.Article
import javax.inject.Inject

class Articles @Inject constructor(
    private val categoryApiService: CategoryApiService
) {
    suspend operator fun invoke():List<Article>{
        try {
            return categoryApiService.getArticles()

        } catch (_: Exception) {
            throw Exception("Article Network Error")
        }


    }
}