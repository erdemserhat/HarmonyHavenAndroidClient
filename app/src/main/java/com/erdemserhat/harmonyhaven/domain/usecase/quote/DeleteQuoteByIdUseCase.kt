package com.erdemserhat.harmonyhaven.domain.usecase.quote

import android.util.Log
import com.erdemserhat.harmonyhaven.data.api.quote.QuoteApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteQuoteByIdUseCase @Inject constructor(
    private val quotesApiService: QuoteApiService
) {
    suspend fun executeRequest(id:Int): Boolean {
        return try {
            val response = withContext(Dispatchers.IO) {
                quotesApiService.deleteQuote(id)
            }
            if (response.isSuccessful) {
                return  true
            } else {
                throw Exception("Notification API call was unsuccessful: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("API-CALL-LOGS", "Error fetching notifications: ${e.message}")
            e.printStackTrace()
            false
        }
    }
}
