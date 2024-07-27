package com.erdemserhat.harmonyhaven.data.api.user

import com.erdemserhat.dto.responses.UserInformationDto
import okhttp3.Response
import retrofit2.http.GET

interface UserInformationApiService {
    @GET("user/get-information")
    suspend fun getUserInformation():retrofit2.Response<UserInformationDto>
}