package com.erdemserhat.harmonyhaven.domain.usecase.article

import android.util.Log
import com.erdemserhat.harmonyhaven.data.network.CategoryApiService
import com.erdemserhat.harmonyhaven.domain.model.rest.ArticleResponseType
import javax.inject.Inject

class GetRecentArticles @Inject constructor(
    private val categoryApiService: CategoryApiService
) {
    suspend operator fun invoke(size:Int):List<ArticleResponseType>?{
        /*
        val response = categoryApiService.getRecentArticles(size)


        if(response.isSuccessful){
            Log.d("erdem3451",response.body().toString())
            return response.body()
        }else{

            return null
        }

         */

        return arrayListOf(ArticleResponseType())
    }
}