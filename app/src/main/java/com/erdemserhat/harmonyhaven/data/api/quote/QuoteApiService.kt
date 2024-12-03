package com.erdemserhat.harmonyhaven.data.api.quote

import com.erdemserhat.harmonyhaven.domain.model.rest.FilteredQuoteRequest
import com.erdemserhat.harmonyhaven.dto.responses.Quote
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Streaming


interface QuoteApiService {

    /**
     * Fetches a list of quotes from the server.
     *
     * @return A [Response] containing a list of [Quote] objects.
     */
    @Streaming
    @GET("v2/get-quotes")
    suspend fun getQuotes(): Response<List<Quote>>

    @POST("v3/get-quotes")
    suspend fun getQuotesV3(
        @Body filter: FilteredQuoteRequest
    ): Response<List<Quote>>

    @POST("v1/like-quote/{quoteId}")
    suspend fun likeQuote(@Path("quoteId") quoteId: Int):Response<Unit>

    @POST("v1/remove-like-quote/{quoteId}")
    suspend fun removeLike(@Path("quoteId") quoteId: Int):Response<Unit>

    @GET("v1/get-liked-quotes")
    suspend fun getAllLikedQuotes():Response<List<Quote>>



    /**
     * Deletes a specific quote by its ID.
     *
     * @param quoteId The ID of the quote to delete.
     * @return A [Response] indicating success or failure.
     */
    @DELETE("v1/delete-quote/{quoteId}")
    suspend fun deleteQuote(@Path("quoteId") quoteId: Int): Response<Unit>


}
