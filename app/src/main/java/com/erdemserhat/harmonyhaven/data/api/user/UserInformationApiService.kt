package com.erdemserhat.harmonyhaven.data.api.user

import com.erdemserhat.harmonyhaven.dto.responses.UserInformationDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface UserInformationApiService {

    /**
     * Retrieves the user's information from the server.
     *
     * This method sends a GET request to the server to fetch the user's information.
     * The server responds with details about the user, encapsulated in a [UserInformationDto] object.
     *
     * @return A [Response] containing a [UserInformationDto] object with the user's information.
     */
    @GET("v1/user/get-information")
    suspend fun getUserInformation(): Response<UserInformationDto>


    /**
     * Retrieves the user's current mood.
     */
    @GET("v1/user/get-mood")
    suspend fun getUserMood(): Response<Map<String, String>> //moodId Int to String

    /**
     * Updates the user's current mood.
     * @param moodUpdate A map containing the new moodId.
     */
    @PATCH("v1/user/update-mood")
    suspend fun updateUserMood(
        @Body moodUpdate: Map<String, String> //moodId Int to String
    ): Response<Map<String, String>>

    /**
     * Retrieves all available moods from the system.
     */
    @GET("v1/user/get-all-moods")
    suspend fun getAllMoods(): Response<List<MoodDto>>


}
data class MoodDto(
    val id: String,
    val name: String,
    val moodImagePath: String
)
