package com.erdemserhat.harmonyhaven.data.api.user

import com.erdemserhat.harmonyhaven.dto.requests.UserInformationSchema
import com.erdemserhat.harmonyhaven.dto.responses.RegistrationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserRegistrationApiService {

    /**
     * Registers a new user with the provided information.
     *
     * This method sends a POST request to the server to register a new user based on the provided
     * user information. The server will process the registration and return a response indicating
     * the result of the registration attempt.
     *
     * @param userInformationSchema The [UserInformationSchema] object containing the user's registration details (e.g., username, password, email).
     * @return A [Response] containing a [RegistrationResponse] object with the result of the registration attempt.
     */
    @POST("v1/user/register")
    suspend fun registerUser(@Body userInformationSchema: UserInformationSchema): Response<RegistrationResponse>
}
