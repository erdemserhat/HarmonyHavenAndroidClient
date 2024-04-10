package com.erdemserhat.harmonyhaven.data.network

import com.erdemserhat.harmonyhaven.dto.requests.password_reset.PasswordResetMailerRequest
import com.erdemserhat.harmonyhaven.dto.responses.password_reset.PasswordResetMailerResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PasswordResetApiService {
    @POST("user/forgot-password/mailer")
    suspend fun sendPasswordResetCode(@Body mail: PasswordResetMailerRequest): Response<PasswordResetMailerResponse>

//TODO("IMPLEMENT LOGIC FOR OTHER ENDPOINTS")


}