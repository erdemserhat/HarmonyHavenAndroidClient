package com.erdemserhat.harmonyhaven.domain.usecase.article

import com.erdemserhat.harmonyhaven.domain.model.rest.Article
import javax.inject.Inject

class GetAllArticles @Inject constructor(
) {
    suspend operator fun invoke():List<Article>{
        try {
            //return categoryApiService.getAllArticles()
            return listOf(Article(1,"",""))

        } catch (_: Exception) {
            throw Exception("Article Network Error")
        }


    }
}