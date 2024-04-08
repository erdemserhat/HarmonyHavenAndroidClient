package com.erdemserhat.harmonyhaven.domain.usecase.users

import com.erdemserhat.harmonyhaven.data.network.UserApiService
import com.erdemserhat.harmonyhaven.dto.requests.UserAuthenticationRequest
import com.erdemserhat.harmonyhaven.dto.responses.AuthenticationResponse
import com.erdemserhat.harmonyhaven.dto.responses.ValidationResult
import javax.inject.Inject

class AuthenticateUser @Inject constructor(
    private val userApiService: UserApiService
) {

    suspend fun executeRequest(userAuthRequest: UserAuthenticationRequest): AuthenticationResponse? {
        try {
            val response = userApiService.authenticateUser(userAuthRequest)
            return response.body()
        } catch (e: Exception) {
            println(e.printStackTrace())
            return null

        }

    }
}