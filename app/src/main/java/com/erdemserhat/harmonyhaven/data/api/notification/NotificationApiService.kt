package com.erdemserhat.harmonyhaven.data.api.notification

import com.erdemserhat.harmonyhaven.dto.responses.NotificationDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NotificationApiService {
    @GET("user/get-notifications")
    suspend fun getNotifications(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<List<NotificationDto>>
}