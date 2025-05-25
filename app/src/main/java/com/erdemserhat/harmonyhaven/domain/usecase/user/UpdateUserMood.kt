package com.erdemserhat.harmonyhaven.domain.usecase.user

import android.util.Log
import com.erdemserhat.harmonyhaven.data.api.user.UserInformationApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateUserMood @Inject constructor(
    private val userInformationApiService: UserInformationApiService
) {
    suspend fun executeRequest(moodId: String): Boolean {
        return try {
            val moodUpdate = mapOf("moodId" to moodId)
            val response = withContext(Dispatchers.IO) {
                userInformationApiService.updateUserMood(moodUpdate)
            }
            if (response.isSuccessful) {
                Log.d("API-CALL-LOGS", "User mood updated successfully")
                true
            } else {
                Log.e("API-CALL-LOGS", "Update user mood API call was unsuccessful: ${response.code()} ${response.message()}")
                false
            }
        } catch (e: Exception) {
            Log.e("API-CALL-LOGS", "Error updating user mood: ${e.message}")
            e.printStackTrace()
            false
        }
    }
} 