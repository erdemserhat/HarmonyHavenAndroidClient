package com.erdemserhat.harmonyhaven.domain.usecase.user

import android.util.Log
import com.erdemserhat.harmonyhaven.data.api.user.UserInformationApiService
import com.erdemserhat.harmonyhaven.domain.model.rest.Mood
import com.erdemserhat.harmonyhaven.domain.model.rest.toDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllMoods @Inject constructor(
    private val userInformationApiService: UserInformationApiService
) {
    suspend fun executeRequest(): List<Mood> {
        return try {
            val response = withContext(Dispatchers.IO) {
                userInformationApiService.getAllMoods()
            }
            if (response.isSuccessful) {
                return response.body()?.toDomainModel() ?: emptyList()
            } else {
                Log.e("API-CALL-LOGS", "Get all moods API call was unsuccessful: ${response.code()} ${response.message()}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("API-CALL-LOGS", "Error fetching all moods: ${e.message}")
            e.printStackTrace()
            emptyList()
        }
    }
} 