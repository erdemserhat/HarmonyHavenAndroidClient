package com.erdemserhat.harmonyhaven.data.api.quote

import retrofit2.Response
import retrofit2.http.GET

interface QuoteApi {
    @GET("v1/user/get-daily-quote") // Endpoint'i daha sonra güncelleyeceksiniz
    suspend fun getDailyQuote(): Response<Map<String,String>>
} 