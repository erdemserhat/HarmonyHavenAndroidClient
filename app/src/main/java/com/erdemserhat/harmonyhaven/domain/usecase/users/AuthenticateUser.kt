package com.erdemserhat.harmonyhaven.domain.usecase.users

import com.erdemserhat.harmonyhaven.data.api.user.UserAuthenticationApiService
import com.erdemserhat.harmonyhaven.dto.requests.UserAuthenticationRequest
import com.erdemserhat.harmonyhaven.dto.responses.AuthenticationResponse
import javax.inject.Inject

class AuthenticateUser @Inject constructor(
    private val userAuthApiService: UserAuthenticationApiService
) {

    suspend fun executeRequest(userAuthRequest: UserAuthenticationRequest): AuthenticationResponse? {
        try {
            val response = userAuthApiService.authenticateUser(userAuthRequest)
            return response.body()
        } catch (e: Exception) {
            println(e.printStackTrace())
            return null

        }

    }
}