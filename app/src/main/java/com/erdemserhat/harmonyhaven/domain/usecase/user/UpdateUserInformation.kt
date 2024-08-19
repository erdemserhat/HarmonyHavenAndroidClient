package com.erdemserhat.harmonyhaven.domain.usecase.user

import android.util.Log
import com.erdemserhat.harmonyhaven.data.api.user.InformationUpdateApiService
import com.erdemserhat.harmonyhaven.dto.requests.UpdateNameDto
import com.erdemserhat.harmonyhaven.dto.requests.UpdatePasswordDto
import com.erdemserhat.harmonyhaven.dto.responses.ValidationResult
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateUserInformation @Inject constructor(
    private val userInformationUpdateApiService: InformationUpdateApiService
) {

    suspend fun updateName(newName: String): Boolean {
        return try {
            val response = withContext(Dispatchers.IO) {
                userInformationUpdateApiService.updateName(UpdateNameDto(newName))
            }
            return response.isSuccessful


        } catch (e: Exception) {
            false
        }

    }

    suspend fun updatePassword(newPassword: String, currentPassword: String): ValidationResult {
        return try {
            val response = withContext(Dispatchers.IO) {
                userInformationUpdateApiService.updatePassword(
                    UpdatePasswordDto(
                        newPassword,
                        currentPassword
                    )
                )
            }
            if (response.isSuccessful) {
                return response.body() ?: ValidationResult(
                    isValid = false,
                    errorMessage = response.message(),
                    errorCode = response.code()
                )
            } else {
                val errorBodyJson = response.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBodyJson, ValidationResult::class.java)
                return errorResponse ?: ValidationResult(
                    isValid = false,
                    errorMessage = response.message(),
                    errorCode = response.code()
                )
            }
        } catch (e: Exception) {
            Log.e("API-CALL-LOGS", "Error updating password: ${e.message}")
            ValidationResult(isValid = false, errorMessage = e.message!!, errorCode = 1)
        }
    }
}
