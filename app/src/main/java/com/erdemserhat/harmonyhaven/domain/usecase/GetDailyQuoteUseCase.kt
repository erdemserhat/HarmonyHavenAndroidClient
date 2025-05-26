package com.erdemserhat.harmonyhaven.domain.usecase

import com.erdemserhat.harmonyhaven.data.api.quote.QuoteApi
import com.erdemserhat.harmonyhaven.domain.model.rest.Result
import javax.inject.Inject

class GetDailyQuoteUseCase @Inject constructor(
    private val quoteApi: QuoteApi
) {
    suspend operator fun invoke(): Result<String> {
        return try {
            val quote = quoteApi.getDailyQuote().body()?.get("daily_quote")!!
            Result.Success(quote)
        } catch (e: Exception) {
            Result.Error(e.message ?: "Günlük söz yüklenirken bir hata oluştu")
        }
    }
}