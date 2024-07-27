package com.erdemserhat.harmonyhaven.data.api.user

import com.erdemserhat.harmonyhaven.dto.requests.UpdateNameDto
import com.erdemserhat.harmonyhaven.dto.requests.UpdatePasswordDto
import com.erdemserhat.harmonyhaven.dto.responses.ValidationResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface InformationUpdateApiService {
    @PATCH("user/update-password")
    suspend fun updatePassword(@Body updatePasswordDto: UpdatePasswordDto): Response<ValidationResult>


    @PATCH("user/update-name")
    suspend fun updateName(@Body updateNameDto: UpdateNameDto):Response<ValidationResult>

}