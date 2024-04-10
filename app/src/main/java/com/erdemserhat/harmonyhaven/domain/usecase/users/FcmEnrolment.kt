package com.erdemserhat.harmonyhaven.domain.usecase.users

import com.erdemserhat.harmonyhaven.data.network.FcmApiService
import com.erdemserhat.harmonyhaven.domain.model.rest.server.RequestResult
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