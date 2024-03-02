package com.erdemserhat.network.retrofit

import com.erdemserhat.model.user.LoginModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

private const val BASE_URL =
    "http://10.196.14.102:8081"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface UserApiService {
    @GET("user")//>photos is endpoint name
    suspend fun getUser():List<LoginModel> //-> kotlinx.serialization with retrofit will handle the process4
}

object UserApi {
    val retrofitService : UserApiService by lazy {
        retrofit.create(UserApiService::class.java)

    }
}

