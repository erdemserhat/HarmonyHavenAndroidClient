package com.erdemserhat.harmonyhaven.domain.usecase.notification

import android.util.Log
import com.erdemserhat.harmonyhaven.data.api.notification.NotificationApiService
import com.erdemserhat.harmonyhaven.data.api.notification.NotificationSchedulerDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ScheduleNotification @Inject constructor(
    private val notificationApiService: NotificationApiService
) {
    suspend fun executeRequest(scheduler: NotificationSchedulerDto): Boolean {
        try {
            val response = withContext(Dispatchers.IO) {
                notificationApiService.schedule(scheduler)
            }
            if (response.isSuccessful) {
                Log.d("API-CALL-LOGS", "Get Notification API call was successful")
                return true
            } else {
                throw Exception("Notification API call was unsuccessful: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("API-CALL-LOGS", "Error scheduling notifications: ${e.message}")
            e.printStackTrace()
            return false

        }
    }
}
