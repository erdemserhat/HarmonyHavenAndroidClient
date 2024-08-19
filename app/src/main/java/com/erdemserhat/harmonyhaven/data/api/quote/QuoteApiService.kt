package com.erdemserhat.harmonyhaven.data.api.quote

import com.erdemserhat.harmonyhaven.dto.responses.Quote
import retrofit2.Response
import retrofit2.http.GET


interface QuoteApiService {

    /**
     * Fetches a list of quotes from the server.
     *
     * @return A [Response] containing a list of [Quote] objects.
     */
    @GET("get-quotes")
    suspend fun getQuotes(): Response<List<Quote>>
}
