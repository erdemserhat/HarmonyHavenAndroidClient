package com.erdemserhat.harmonyhaven.domain.usecase.notification

import android.util.Log
import com.erdemserhat.harmonyhaven.data.api.notification.NotificationApiService
import com.erdemserhat.harmonyhaven.dto.responses.NotificationDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteScheduler @Inject constructor(
    private val notificationApiService: NotificationApiService
) {
    suspend fun executeRequest(id:String):Boolean{
         try {
            val response = withContext(Dispatchers.IO) {
                notificationApiService.deleteScheduler(id)
            }
            if (response.isSuccessful) {
                Log.d("API-CALL-LOGS", "delete Notification API call was successful")
                return true
            } else {
                throw Exception("Notification API call was unsuccessful: ${response.code()} ${response.message()}")

            }
        } catch (e: Exception) {
            Log.e("API-CALL-LOGS", "Error deleting notifications: ${e.message}")
            e.printStackTrace()
             return false
         }
    }
}
