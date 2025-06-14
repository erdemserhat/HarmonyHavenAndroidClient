package com.erdemserhat.harmonyhaven.domain.usecase.notification

import android.util.Log
import com.erdemserhat.harmonyhaven.data.api.notification.NotificationApiService
import com.erdemserhat.harmonyhaven.dto.responses.NotificationDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetNotificationCount @Inject constructor(
    private val notificationApiService: NotificationApiService
) {
    suspend fun executeRequest(): Int {
        return try {
            val response = withContext(Dispatchers.IO) {
                notificationApiService.getNotificationCount()
            }
            if (response.isSuccessful) {
                Log.d("API-CALL-LOGS", "Get Notification API call was successful")
                response.body()?.get("size") ?: throw Exception("Request successful, but response was null")
            } else {
                throw Exception("Notification API call was unsuccessful: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("API-CALL-LOGS", "Error fetching notifications: ${e.message}")
            e.printStackTrace()
            0
        }
    }
}
