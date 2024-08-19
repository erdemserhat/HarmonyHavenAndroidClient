package com.erdemserhat.harmonyhaven.data.api.user

import com.erdemserhat.harmonyhaven.dto.requests.UpdateNameDto
import com.erdemserhat.harmonyhaven.dto.requests.UpdatePasswordDto
import com.erdemserhat.harmonyhaven.dto.responses.ValidationResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH

interface InformationUpdateApiService {

    /**
     * Updates the user's password.
     *
     * This method sends a PATCH request to the server to update the user's password.
     * The request body contains the new password and any required validation information.
     *
     * @param updatePasswordDto The [UpdatePasswordDto] object containing the new password and validation details.
     * @return A [Response] containing a [ValidationResult] object that provides information on the success or failure of the update.
     */
    @PATCH("user/update-password")
    suspend fun updatePassword(@Body updatePasswordDto: UpdatePasswordDto): Response<ValidationResult>

    /**
     * Updates the user's name.
     *
     * This method sends a PATCH request to the server to update the user's name.
     * The request body contains the new name details.
     *
     * @param updateNameDto The [UpdateNameDto] object containing the new name information.
     * @return A [Response] containing a [ValidationResult] object that provides information on the success or failure of the update.
     */
    @PATCH("user/update-name")
    suspend fun updateName(@Body updateNameDto: UpdateNameDto): Response<ValidationResult>
}
