package com.erdemserhat.harmonyhaven.domain.usecase.user

import android.util.Log
import com.erdemserhat.harmonyhaven.data.api.user.UserAuthenticationApiService
import com.erdemserhat.harmonyhaven.dto.requests.GoogleAuthenticationRequest
import com.erdemserhat.harmonyhaven.dto.requests.UserAuthenticationRequest
import com.erdemserhat.harmonyhaven.dto.responses.AuthenticationResponse
import com.erdemserhat.harmonyhaven.dto.responses.GoogleAuthenticationResponse
import com.google.gson.Gson
import javax.inject.Inject

class AuthenticateUserViaGoogle @Inject constructor(
    private val userAuthApiService: UserAuthenticationApiService

) {

    suspend fun executeRequest(userAuthRequest: GoogleAuthenticationRequest): GoogleAuthenticationResponse? {
        //Log.d("authenticate_endpoint_test","e.message.toString()")
        try {
            val response = userAuthApiService.authenticateUserViaGoogle(userAuthRequest)
            if (response.isSuccessful) {
                return response.body()
            } else {
                Log.d("Google Authentication Request", response.errorBody().toString())
                val errorBodyJson = response.errorBody()?.string()
                val errorResponse =
                    Gson().fromJson(errorBodyJson, AuthenticationResponse::class.java)

                return null
            }


        } catch (e: Exception) {
            println(e.printStackTrace())
            return null

        }

    }
}