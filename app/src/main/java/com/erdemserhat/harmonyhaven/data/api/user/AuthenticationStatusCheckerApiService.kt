package com.erdemserhat.harmonyhaven.data.api.user

import retrofit2.Response
import retrofit2.http.GET

interface AuthenticationStatusCheckerApiService {
    @GET("check-auth-status")
    suspend fun checkAuthenticationStatus(): Response<Unit>
}