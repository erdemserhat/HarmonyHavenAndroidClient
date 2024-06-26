package com.erdemserhat.harmonyhaven.domain.usecase.article

import com.erdemserhat.harmonyhaven.domain.model.rest.ArticleResponseType
import javax.inject.Inject

class GetArticlesByCategory @Inject constructor(
) {
    suspend operator fun invoke(categoryId: Int): List<ArticleResponseType>? {
        /*


        val response = categoryApiService.getArticlesByCategory(categoryId)

        if (response.isSuccessful) {
            Log.d("erdem45", response.body().toString())
            return response.body()
        } else {

            return null
        }

         */

        return listOf(ArticleResponseType())
    }
}