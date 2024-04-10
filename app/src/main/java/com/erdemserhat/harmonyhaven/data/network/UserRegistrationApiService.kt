package com.erdemserhat.harmonyhaven.data.network

import com.erdemserhat.harmonyhaven.dto.requests.UserInformationSchema
import com.erdemserhat.harmonyhaven.dto.responses.RegistrationResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserRegistrationApiService {
    @POST("user/register")
    suspend fun registerUser(@Body userInformationSchema: UserInformationSchema):Response<RegistrationResponse>
}