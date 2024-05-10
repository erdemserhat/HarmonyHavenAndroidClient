package com.erdemserhat.harmonyhaven.domain.usecase.article

import com.erdemserhat.harmonyhaven.data.api.article.ArticleApiService
import com.erdemserhat.harmonyhaven.domain.model.rest.Article
import javax.inject.Inject

class GetArticles @Inject constructor(
    private val articleApiService: ArticleApiService
) {
    suspend fun executeRequest():List<Article>?{
        try {
            val response = articleApiService.getArticles()
            response.let {
                if(response.isSuccessful)
                    return response.body()!!
                else return listOf()
            }
        }catch (e:Exception){
            return null
        }
    }
}