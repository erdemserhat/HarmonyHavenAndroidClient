package com.erdemserhat.harmonyhaven.domain.usecase.article

import com.erdemserhat.harmonyhaven.domain.model.rest.ArticleResponseType
import javax.inject.Inject

class GetArticleById @Inject constructor(
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