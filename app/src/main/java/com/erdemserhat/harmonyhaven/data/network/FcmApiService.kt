package com.erdemserhat.harmonyhaven.data.network

import com.erdemserhat.harmonyhaven.dto.requests.FcmSetupRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface FcmApiService {
    @POST("user/fcm-enrolment")
    suspend fun updateFcmId(@Body fcmSetup:FcmSetupRequest):Response<Void>


}