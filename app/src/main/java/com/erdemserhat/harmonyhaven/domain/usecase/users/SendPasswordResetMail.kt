package com.erdemserhat.harmonyhaven.domain.usecase.users

import com.erdemserhat.harmonyhaven.data.network.PasswordResetApiService
import com.erdemserhat.harmonyhaven.dto.requests.password_reset.PasswordResetMailerRequest
import com.erdemserhat.harmonyhaven.dto.responses.password_reset.PasswordResetMailerResponse
import com.google.gson.Gson
import javax.inject.Inject

class SendPasswordResetMail @Inject constructor(
    private val passwordResetApiService: PasswordResetApiService
) {
    suspend fun executeRequest(email: String): PasswordResetMailerResponse? {
        val mailer = PasswordResetMailerRequest(email)
        val response = passwordResetApiService.sendPasswordResetCode(mailer)

        if (response.isSuccessful)
            return response.body()
        else {
            val errorBodyJson = response.errorBody()!!.string()
            val mailerErrorResponse =
                Gson().fromJson(errorBodyJson, PasswordResetMailerResponse::class.java)
            return mailerErrorResponse


        }


    }
}