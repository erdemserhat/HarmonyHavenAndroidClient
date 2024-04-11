package com.erdemserhat.harmonyhaven.domain.usecase.users

import com.erdemserhat.harmonyhaven.data.api.user.UserRegistrationApiService
import com.erdemserhat.harmonyhaven.dto.requests.UserInformationSchema
import com.erdemserhat.harmonyhaven.dto.responses.RegistrationResponse
import com.google.gson.Gson
import javax.inject.Inject

class RegisterUser @Inject constructor(
    private val userRegistrationApiService: UserRegistrationApiService
) {
    suspend fun executeRequest(userInformationSchema: UserInformationSchema): RegistrationResponse? {
        try {
            val response = userRegistrationApiService.registerUser(userInformationSchema)
            if (response.isSuccessful)
                return response.body()
            else {
                val errorBodyJson = response.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBodyJson, RegistrationResponse::class.java)
                return errorResponse
            }

        } catch (e: Exception) {
            return null
        }
    }
}