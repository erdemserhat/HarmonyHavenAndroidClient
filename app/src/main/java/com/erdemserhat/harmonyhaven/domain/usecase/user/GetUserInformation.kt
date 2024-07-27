package com.erdemserhat.harmonyhaven.domain.usecase.user

import android.util.Log
import com.erdemserhat.dto.responses.UserInformationDto
import com.erdemserhat.harmonyhaven.data.api.user.UserInformationApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetUserInformation @Inject constructor(
    private val userInformationApiService: UserInformationApiService
) {
    suspend fun executeRequest(): UserInformationDto {
        return try {
            val response = withContext(Dispatchers.IO) {
                userInformationApiService.getUserInformation()
            }
            if (response.isSuccessful) {
                return response.body() ?: throw Exception("response was null")
            } else {
                throw Exception("Notification API call was unsuccessful: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("API-CALL-LOGS", "Error fetching notifications: ${e.message}")
            e.printStackTrace()
            UserInformationDto()
        }

    }
}