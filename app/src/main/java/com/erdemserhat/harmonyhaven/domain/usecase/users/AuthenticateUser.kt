package com.erdemserhat.harmonyhaven.domain.usecase.users

import android.util.Log
import com.erdemserhat.harmonyhaven.data.api.user.UserAuthenticationApiService
import com.erdemserhat.harmonyhaven.dto.requests.UserAuthenticationRequest
import com.erdemserhat.harmonyhaven.dto.responses.AuthenticationResponse
import com.erdemserhat.harmonyhaven.dto.responses.RegistrationResponse
import com.google.gson.Gson
import javax.inject.Inject

class AuthenticateUser @Inject constructor(
    private val userAuthApiService: UserAuthenticationApiService
) {

    suspend fun executeRequest(userAuthRequest: UserAuthenticationRequest): AuthenticationResponse? {
        //Log.d("authenticate_endpoint_test","e.message.toString()")
        try {
            val response = userAuthApiService.authenticateUser(userAuthRequest)
            if (response.isSuccessful) {


                return response.body()
            } else {
                Log.d("erdem3451", response.errorBody().toString())
                val errorBodyJson = response.errorBody()?.string()
                val errorResponse =
                    Gson().fromJson(errorBodyJson, AuthenticationResponse::class.java)

                return errorResponse
            }


        } catch (e: Exception) {
            Log.d("erdem3451", e.message.toString())
            println(e.printStackTrace())
            return null

        }

    }
}