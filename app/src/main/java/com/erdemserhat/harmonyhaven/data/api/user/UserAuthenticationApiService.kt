package com.erdemserhat.harmonyhaven.data.api.user

import com.erdemserhat.harmonyhaven.dto.requests.UserAuthenticationRequest
import com.erdemserhat.harmonyhaven.dto.responses.AuthenticationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserAuthenticationApiService {
    @POST("user/authenticate")
    suspend fun authenticateUser(@Body userAuthRequest: UserAuthenticationRequest): Response<AuthenticationResponse>
}