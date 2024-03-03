package com.erdemserhat.harmonyhaven.network.user

import com.erdemserhat.harmonyhaven.domain.model.Message
import com.erdemserhat.harmonyhaven.domain.model.User
import com.erdemserhat.harmonyhaven.domain.model.UserLogin
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Inject

interface UserApiService {
    @POST("user")
    fun login(@Body loginModel: UserLogin):Call<User>
    @POST("user")
    fun register(@Body user:User):Call<User>
    @POST("user")
    fun updateUser(@Body user:User):Call<User>
    @POST("user")
    fun deleteUser(@Body user:User):Call<Message>

}
