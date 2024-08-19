package com.erdemserhat.harmonyhaven.data.api.user

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

    /**
     * Sends a password reset code to the user's email address.
     *
     * This method sends a POST request to the server to initiate the password reset process by sending
     * a reset code to the specified email address.
     *
     * @param mail The [PasswordResetMailerRequest] object containing the email address where the reset code should be sent.
     * @return A [Response] containing a [PasswordResetMailerResponse] object with details of the reset code request.
     */
    @POST("user/forgot-password/mailer")
    suspend fun sendPasswordResetCode(@Body mail: PasswordResetMailerRequest): Response<PasswordResetMailerResponse>

    /**
     * Authenticates the password reset attempt using the provided code.
     *
     * This method sends a POST request to verify the reset code provided by the user and authenticate
     * the password reset attempt.
     *
     * @param auth The [PasswordResetAuthenticateRequest] object containing the reset code and any additional authentication details.
     * @return A [Response] containing a [PasswordResetAuthenticateResponse] object with details of the authentication attempt.
     */
    @POST("user/forgot-password/auth")
    suspend fun authenticateAttempt(@Body auth: PasswordResetAuthenticateRequest): Response<PasswordResetAuthenticateResponse>

    /**
     * Completes the password reset process by setting a new password.
     *
     * This method sends a PATCH request to finalize the password reset process by applying the new password
     * provided by the user.
     *
     * @param finalRequest The [PasswordResetFinalRequest] object containing the new password and any required details.
     * @return A [Response] containing a [PasswordResetFinalResponse] object indicating the success or failure of the password reset.
     */
    @PATCH("user/forgot-password/reset-password")
    suspend fun completePasswordResetAttempt(@Body finalRequest: PasswordResetFinalRequest): Response<PasswordResetFinalResponse>
}
