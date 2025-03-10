package com.erdemserhat.harmonyhaven.domain.usecase

import android.util.Log
import com.erdemserhat.harmonyhaven.data.api.VersionCheckingApiService
import com.erdemserhat.harmonyhaven.domain.model.rest.CommentRequest
import com.erdemserhat.harmonyhaven.dto.responses.Quote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VersionControlUseCase @Inject constructor(
    private val versionCheckingApiService: VersionCheckingApiService
){
    suspend fun executeRequest(currentVersion:Int): Int {
        return try {
            val response = withContext(Dispatchers.IO) {
                Log.d("dsfsdfsd","11111")
                versionCheckingApiService.checkVersionStability(currentVersion)

            }
            Log.d("dsfsdfsd","222222")
            if (response.isSuccessful) {
                Log.d("API-CALL-LOGS", "Get Notification API call was successful")
                val result = response.body()?.result
                return result!!

            } else {
                throw Exception("Notification API call was unsuccessful: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("API-CALL-LOGS", "Error fetching notifications: ${e.message}")
            e.printStackTrace()
            -2
        }
    }

}
