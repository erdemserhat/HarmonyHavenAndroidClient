package com.erdemserhat.harmonyhaven.data.api.user

import com.erdemserhat.harmonyhaven.dto.requests.UserAuthenticationRequest
import com.erdemserhat.harmonyhaven.dto.responses.AuthenticationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserAuthenticationApiService {

    /**
     * Authenticates a user with the provided credentials.
     *
     * This method sends a POST request to the server to authenticate the user based on the provided
     * authentication details. The server will verify the credentials and return an authentication response.
     *
     * @param userAuthRequest The [UserAuthenticationRequest] object containing the user's credentials (e.g., username, password).
     * @return A [Response] containing an [AuthenticationResponse] object with the result of the authentication attempt.
     */
    @POST("user/authenticate")
    suspend fun authenticateUser(@Body userAuthRequest: UserAuthenticationRequest): Response<AuthenticationResponse>
}
