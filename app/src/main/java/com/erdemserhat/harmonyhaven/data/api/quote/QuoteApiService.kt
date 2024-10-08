package com.erdemserhat.harmonyhaven.data.api.quote

import com.erdemserhat.harmonyhaven.dto.responses.Quote
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path


interface QuoteApiService {

    /**
     * Fetches a list of quotes from the server.
     *
     * @return A [Response] containing a list of [Quote] objects.
     */
    @GET("get-quotes")
    suspend fun getQuotes(): Response<List<Quote>>


    /**
     * Deletes a specific quote by its ID.
     *
     * @param quoteId The ID of the quote to delete.
     * @return A [Response] indicating success or failure.
     */
    @DELETE("delete-quote/{quoteId}")
    suspend fun deleteQuote(@Path("quoteId") quoteId: Int): Response<Unit>
}
