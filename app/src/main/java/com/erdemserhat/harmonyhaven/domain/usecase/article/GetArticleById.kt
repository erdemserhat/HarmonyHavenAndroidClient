package com.erdemserhat.harmonyhaven.domain.usecase.article

import android.util.Log
import com.erdemserhat.harmonyhaven.data.network.CategoryApiService
import com.erdemserhat.harmonyhaven.domain.model.rest.Article
import com.erdemserhat.harmonyhaven.domain.model.rest.ArticleResponseType
import javax.inject.Inject

class GetArticleById @Inject constructor(
    private val categoryApiService: CategoryApiService
) {
    suspend operator fun invoke(articleId: Int): ArticleResponseType? {
     /*
        val response = categoryApiService.getArticleById(articleId)

        if(response.isSuccessful){
            Log.d("erdem3451",response.body().toString())
            return response.body()
        }else{

            return null
        }


      */
        return ArticleResponseType()

    }


}