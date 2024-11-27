package com.erdemserhat.harmonyhaven.domain.usecase.quote

import com.erdemserhat.harmonyhaven.data.api.quote.QuoteApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoveLikeQuote @Inject constructor(
    private val quotesApiService: QuoteApiService
) {
    suspend fun executeRequest(quoteId: Int) {
        try {
            val response = withContext(Dispatchers.IO) {
                quotesApiService.removeLike(quoteId)
            }
            if (response.isSuccessful) {
                //Log.d("API-CALL-LOGS", "Get Notification API call was successful")
                response.body() ?: throw Exception("Request successful, but response was null")
            } else {
                throw Exception("Notification API call was unsuccessful: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            //Log.e("API-CALL-LOGS", "Error fetching notifications: ${e.message}")
            //e.printStackTrace()
            //emptyList()
        }

    }
}