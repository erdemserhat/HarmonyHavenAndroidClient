package com.erdemserhat.harmonyhaven.data.network

import com.erdemserhat.harmonyhaven.dto.requests.password_reset.PasswordResetAuthenticateRequest
import com.erdemserhat.harmonyhaven.dto.requests.password_reset.PasswordResetFinalRequest
import com.erdemserhat.harmonyhaven.dto.requests.password_reset.PasswordResetMailerRequest
import com.erdemserhat.harmonyhaven.dto.responses.password_reset.PasswordResetAuthenticateResponse
import com.erdemserhat.harmonyhaven.dto.responses.password_reset.PasswordResetFinalResponse
import com.erdemserhat.harmonyhaven.dto.responses.password_reset.PasswordResetMailerResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface PasswordResetApiService {
    @POST("user/forgot-password/mailer")
    suspend fun sendPasswordResetCode(@Body mail: PasswordResetMailerRequest): Response<PasswordResetMailerResponse>

    @POST("user/forgot-password/auth")
    suspend fun authenticateAttempt(@Body auth:PasswordResetAuthenticateRequest):Response<PasswordResetAuthenticateResponse>

    @PATCH("user/forgot-password/reset-password")
    suspend fun completePasswordResetAttempt(@Body final:PasswordResetFinalRequest):Response<PasswordResetFinalResponse>


}