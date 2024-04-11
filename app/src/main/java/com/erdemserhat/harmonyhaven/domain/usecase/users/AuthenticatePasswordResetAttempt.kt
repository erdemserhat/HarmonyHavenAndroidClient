package com.erdemserhat.harmonyhaven.domain.usecase.users

import com.erdemserhat.harmonyhaven.data.network.PasswordResetApiService
import com.erdemserhat.harmonyhaven.dto.requests.password_reset.PasswordResetAuthenticateRequest
import com.erdemserhat.harmonyhaven.dto.responses.password_reset.PasswordResetAuthenticateResponse
import com.google.gson.Gson
import javax.inject.Inject

class AuthenticatePasswordResetAttempt @Inject constructor(
    private val passwordResetApiService: PasswordResetApiService
) {
    suspend fun executeRequest(auth:PasswordResetAuthenticateRequest):PasswordResetAuthenticateResponse?{
        try {
            val response = passwordResetApiService.authenticateAttempt(auth)
            if(response.isSuccessful)
                return response.body()
            else{
                val errorBodyJson = response.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBodyJson,PasswordResetAuthenticateResponse::class.java)
                return errorResponse


            }
        }catch (e:Exception){
            return null
        }




    }


}
