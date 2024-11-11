package com.erdemserhat.harmonyhaven.data.api.fcm

import com.erdemserhat.harmonyhaven.dto.requests.FcmSetupRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interface for defining API endpoints related to Firebase Cloud Messaging (FCM).
 * This service is responsible for updating the FCM token on the server.
 */
interface FcmApiService {

    /**
     * Sends a POST request to enroll or update the FCM ID (token) for a user.
     *
     * @param fcmSetup An instance of [FcmSetupRequest] containing the user's FCM token.
     * @return A [Response] object with no content (Void), which can be checked to ensure
     *         that the request was successful.
     */
    @POST("v1/user/fcm-enrolment")
    suspend fun updateFcmId(
        @Body fcmSetup: FcmSetupRequest // The request body containing the FCM token details
    ): Response<Void>
}