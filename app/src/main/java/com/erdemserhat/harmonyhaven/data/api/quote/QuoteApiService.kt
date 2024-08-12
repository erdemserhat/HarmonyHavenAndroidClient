package com.erdemserhat.harmonyhaven.data.api.quote

import com.erdemserhat.harmonyhaven.domain.model.rest.Article
import com.erdemserhat.harmonyhaven.domain.model.rest.Category
import com.erdemserhat.harmonyhaven.dto.responses.Quote
import retrofit2.Response
import retrofit2.http.GET


interface QuoteApiService {
    @GET("get-quotes")
    suspend fun getQuotes(): Response<List<Quote>>
}