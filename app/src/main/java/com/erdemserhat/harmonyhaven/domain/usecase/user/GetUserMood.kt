package com.erdemserhat.harmonyhaven.domain.usecase.user

import android.util.Log
import com.erdemserhat.harmonyhaven.data.api.user.UserInformationApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetUserMood @Inject constructor(
    private val userInformationApiService: UserInformationApiService
) {
    suspend fun executeRequest(): Map<String, String>? {
        return try {
            val response = withContext(Dispatchers.IO) {
                userInformationApiService.getUserMood()
            }
            if (response.isSuccessful) {
                return response.body()
            } else {
                Log.e("API-CALL-LOGS", "Get user mood API call was unsuccessful: ${response.code()} ${response.message()}")
                null
            }
        } catch (e: Exception) {
            Log.e("API-CALL-LOGS", "Error fetching user mood: ${e.message}")
            e.printStackTrace()
            null
        }
    }
} 