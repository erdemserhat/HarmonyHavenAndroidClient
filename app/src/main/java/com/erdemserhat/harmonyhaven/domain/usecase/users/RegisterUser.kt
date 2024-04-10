package com.erdemserhat.harmonyhaven.domain.usecase.users

import android.util.Log
import com.erdemserhat.harmonyhaven.data.network.UserApiService
import com.erdemserhat.harmonyhaven.data.network.UserRegistrationApiService
import com.erdemserhat.harmonyhaven.domain.model.rest.client.RequestResultClient
import com.erdemserhat.harmonyhaven.domain.model.rest.User
import com.erdemserhat.harmonyhaven.dto.requests.UserInformationSchema
import com.erdemserhat.harmonyhaven.dto.responses.RegistrationResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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