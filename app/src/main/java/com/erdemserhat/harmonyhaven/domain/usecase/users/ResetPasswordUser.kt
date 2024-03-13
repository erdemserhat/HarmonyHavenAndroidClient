package com.erdemserhat.harmonyhaven.domain.usecase.users

import com.erdemserhat.harmonyhaven.data.network.UserApiService
import com.erdemserhat.harmonyhaven.domain.model.PasswordResetModel
import com.erdemserhat.harmonyhaven.domain.model.RequestResult
import com.erdemserhat.harmonyhaven.domain.model.RequestResultClient
import com.erdemserhat.harmonyhaven.domain.model.ResetPasswordRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ResetPasswordUser @Inject constructor(
    private val userApiService: UserApiService
) {
    suspend fun sendMail(email:String): Flow<RequestResultClient> = flow{
        emit(RequestResultClient(false,"Loading...",true))

        val resetPasswordRequest = ResetPasswordRequest(email)
        val response = userApiService.sendPasswordResetMail(resetPasswordRequest)
        val result = response.body()?.result ?: false
        val message = response.body()?.message ?: "An error occurred"

        emit(RequestResultClient(result,message,false))


    }

    suspend fun resetPassword(code:String,newPassword:String):Flow<RequestResultClient> = flow{
        emit(RequestResultClient(false,"Loading...",true))

        val passwordResetModel = PasswordResetModel(code,newPassword)
        val response = userApiService.resetPassword(passwordResetModel)

        val result = response.body()?.result ?:false
        val message = response.body()?.message ?:"An error occurred"

        emit(RequestResultClient(result,message,false))


    }
}