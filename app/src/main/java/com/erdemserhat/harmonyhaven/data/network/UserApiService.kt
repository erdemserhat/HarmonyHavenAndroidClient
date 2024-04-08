package com.erdemserhat.harmonyhaven.data.network

import com.erdemserhat.harmonyhaven.domain.model.rest.client.ForgotPasswordAuthClientModel
import com.erdemserhat.harmonyhaven.domain.model.rest.client.ForgotPasswordMailerClientModel
import com.erdemserhat.harmonyhaven.domain.model.rest.client.ForgotPasswordResetClientModel
import com.erdemserhat.harmonyhaven.domain.model.rest.User
import com.erdemserhat.harmonyhaven.domain.model.rest.client.UserLogin
import com.erdemserhat.harmonyhaven.domain.model.rest.server.RequestResult
import com.erdemserhat.harmonyhaven.domain.model.rest.server.RequestResultUUID
import com.erdemserhat.harmonyhaven.domain.model.rest.client.UserUpdateModel
import com.erdemserhat.harmonyhaven.dto.requests.UserAuthenticationRequest
import com.erdemserhat.harmonyhaven.dto.responses.AuthenticationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface UserApiService {

    @POST("user/authenticate")
    suspend fun authenticateUser(@Body userAuthRequest: UserAuthenticationRequest):Response<AuthenticationResponse>

   // @POST("user/")

    /*
    @POST("/user/login")
    suspend fun login(@Body loginModel: UserLogin):Response<RequestResult>
    @POST("/user/register")
    suspend fun register(@Body user: User):Response<RequestResult>
    @PATCH("user/update")


    suspend fun updateUser(@Body userUpdateModel: UserUpdateModel):Response<RequestResult>
    @PATCH("user/delete")
    suspend fun deleteUser(@Body userLogin: UserLogin):Response<RequestResult>

    /**
     * This endpoints sends mail in order to reset password based on user's request
     */

    @POST("user/forgot-password/mailer") ///-------->Server side endpoint name (Mailer)
    suspend fun requestResetPasswordMail(@Body mailerModel: ForgotPasswordMailerClientModel):Response<RequestResult>

    /**
     * This endpoint refers the uuid generation based on the mailer request
     */

    @PATCH("user/forgot-password/auth") ///-------->Server side endpoint name (Auth)
    suspend fun requestResetPasswordAuth(@Body authModel: ForgotPasswordAuthClientModel):Response<RequestResultUUID>

    /**
     * This endpoint is the last step of password reset progress
     */

    @PATCH("user/forgot-password/reset-password")
    suspend fun requestPasswordReset(@Body resetModel: ForgotPasswordResetClientModel):Response<RequestResult>


     */



}
