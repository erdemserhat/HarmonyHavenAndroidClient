package com.erdemserhat.harmonyhaven.data.network

import com.erdemserhat.harmonyhaven.domain.model.Message
import com.erdemserhat.harmonyhaven.domain.model.User
import com.erdemserhat.harmonyhaven.domain.model.UserLogin
import com.erdemserhat.harmonyhaven.domain.model.PasswordResetModel
import com.erdemserhat.harmonyhaven.domain.model.RequestResult
import com.erdemserhat.harmonyhaven.domain.model.ResetPasswordRequest
import com.erdemserhat.harmonyhaven.domain.model.UserUpdateModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PATCH
import retrofit2.http.POST

interface UserApiService {
    @POST("/user/login")
    suspend fun login(@Body loginModel: UserLogin):Response<RequestResult>
    @POST("/user/register")
    suspend fun register(@Body user:User):Response<RequestResult>
    @PATCH("user/update")
    suspend fun updateUser(@Body userUpdateModel: UserUpdateModel):Response<RequestResult>
    @PATCH("user/delete")
    suspend fun deleteUser(@Body userLogin: UserLogin):Response<RequestResult>
    @POST("/user/reset-password/auth")
    suspend fun sendPasswordResetMail(@Body resetPasswordRequest: ResetPasswordRequest):Response<Message>

    @PATCH("/user/resetpassword/confirm")
    suspend fun resetPassword(@Body passwordResetModel: PasswordResetModel):Response<Message>

}
