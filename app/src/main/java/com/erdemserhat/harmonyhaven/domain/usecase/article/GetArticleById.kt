package com.erdemserhat.harmonyhaven.domain.usecase.article

import com.erdemserhat.harmonyhaven.data.api.article.ArticleApiService
import com.erdemserhat.harmonyhaven.domain.model.rest.Article
import com.erdemserhat.harmonyhaven.domain.model.rest.ArticleResponseType
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetArticleById @Inject constructor(
    private val articleApiService: ArticleApiService
) {
    suspend fun executeRequest(id: Int): ArticleResponseType? {
        return try {
            val response = articleApiService.getArticleById(id)
            if (response.isSuccessful) {
                response.body() // Successful response, return the article
            } else {
                // Handle non-successful HTTP status codes
                throw HttpException(response)
            }
        } catch (e: IOException) {
            // Handle network errors (e.g., no internet connection)
            e.printStackTrace()
            null
        } catch (e: HttpException) {
            // Handle HTTP errors (e.g., 404, 500)
            e.printStackTrace()
            null
        } catch (e: Exception) {
            // Handle unexpected exceptions
            e.printStackTrace()
            null
        }
    }
}
