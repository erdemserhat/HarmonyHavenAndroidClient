package com.erdemserhat.harmonyhaven.network

import com.erdemserhat.harmonyhaven.domain.model.Message
import com.erdemserhat.harmonyhaven.domain.model.User
import com.erdemserhat.harmonyhaven.domain.model.UserLogin
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import javax.inject.Inject

interface UserApiService {
    @POST("/user/login")
    suspend fun login(@Body loginModel: UserLogin):Response<User>
    @POST("/user/register")
    suspend fun register(@Body user:User):Response<Message>
    @POST("user")
    suspend fun updateUser(@Body user:User):Response<User>
    @POST("user")
    suspend fun deleteUser(@Body user:User):Response<Message>

}
