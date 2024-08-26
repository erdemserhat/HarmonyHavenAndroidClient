package com.erdemserhat.harmonyhaven.data.api.user

import com.erdemserhat.harmonyhaven.dto.responses.UserInformationDto
import retrofit2.Response
import retrofit2.http.GET

interface UserInformationApiService {

    /**
     * Retrieves the user's information from the server.
     *
     * This method sends a GET request to the server to fetch the user's information.
     * The server responds with details about the user, encapsulated in a [UserInformationDto] object.
     *
     * @return A [Response] containing a [UserInformationDto] object with the user's information.
     */
    @GET("user/get-information")
    suspend fun getUserInformation(): Response<UserInformationDto>
}
