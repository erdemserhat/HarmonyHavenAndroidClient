package com.erdemserhat.harmonyhaven.domain.usecase.quote

import android.util.Log
import com.erdemserhat.harmonyhaven.data.api.quote.QuoteApiService
import com.erdemserhat.harmonyhaven.dto.responses.Quote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetLikedQuotes @Inject constructor(
    private val quotesApiService: QuoteApiService
) {
    suspend fun executeRequest(): List<Quote> {
        return try {
            val response = withContext(Dispatchers.IO) {
                quotesApiService.getAllLikedQuotes()
            }
            if (response.isSuccessful) {
                Log.d("API-CALL-LOGS", "Get Notification API call was successful")
                response.body()?: throw Exception("Request successful, but response was null")
            } else {
                throw Exception("Notification API call was unsuccessful: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("API-CALL-LOGS", "Error fetching notifications: ${e.message}")
            e.printStackTrace()
            emptyList()
        }
    }
}
