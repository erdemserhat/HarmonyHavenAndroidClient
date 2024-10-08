package com.erdemserhat.harmonyhaven.domain.usecase.user

import com.erdemserhat.harmonyhaven.data.api.fcm.FcmApiService
import com.erdemserhat.harmonyhaven.domain.model.rest.RequestResult
import com.erdemserhat.harmonyhaven.dto.requests.FcmSetupRequest
import javax.inject.Inject

class FcmEnrolment @Inject constructor(
    private val fcmApi: FcmApiService
) {
    suspend fun executeRequest(fcmId: String): RequestResult {
        try {
            val response = fcmApi.updateFcmId(FcmSetupRequest(fcmId))
            if (response.isSuccessful) {
                return RequestResult(true,response.toString())
            } else {
                return RequestResult(true,response.toString())
            }

        } catch (e: Exception) {
            return RequestResult(true,e.message.toString())
        }

    }

}