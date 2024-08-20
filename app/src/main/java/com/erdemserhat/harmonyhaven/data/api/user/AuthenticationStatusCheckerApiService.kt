package com.erdemserhat.harmonyhaven.data.api.user

import retrofit2.Response
import retrofit2.http.GET

interface AuthenticationStatusCheckerApiService {

    /**
     * Checks the authentication status of the user.
     *
     * This method sends a request to the server to verify if the user is authenticated.
     * The server response will indicate whether the user is currently authenticated.
     *
     * @return A [Response] containing a [Unit] object. The response status code will reflect the authentication status.
     */
    @GET("check-auth-status")
    suspend fun checkAuthenticationStatus(): Response<Unit>
}
