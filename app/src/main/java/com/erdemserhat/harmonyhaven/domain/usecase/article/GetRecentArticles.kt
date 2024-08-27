package com.erdemserhat.harmonyhaven.domain.usecase.article

import com.erdemserhat.harmonyhaven.domain.model.rest.ArticlePresentableUIModel
import javax.inject.Inject

class GetRecentArticles @Inject constructor(
) {
    suspend operator fun invoke(size:Int): List<ArticlePresentableUIModel> {
        /*
        val response = categoryApiService.getRecentArticles(size)


        if(response.isSuccessful){
            Log.d("erdem3451",response.body().toString())
            return response.body()
        }else{

            return null
        }

         */

        return arrayListOf(ArticlePresentableUIModel())
    }
}