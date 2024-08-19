package com.erdemserhat.harmonyhaven.data.api.notification

import com.erdemserhat.harmonyhaven.dto.responses.NotificationDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NotificationApiService {

    /**
     * Fetches a paginated list of notifications for the user.
     *
     * @param page The page number to retrieve (starting from 0).
     * @param size The number of notifications to retrieve per page.
     * @return A [Response] containing a list of [NotificationDto] objects.
     */
    @GET("user/get-notifications")
    suspend fun getNotifications(
        @Query("page") page: Int,  // The page number of notifications to retrieve.
        @Query("size") size: Int   // The number of notifications per page.
    ): Response<List<NotificationDto>>
}
