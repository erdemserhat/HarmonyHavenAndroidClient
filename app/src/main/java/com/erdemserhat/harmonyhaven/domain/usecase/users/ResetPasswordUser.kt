package com.erdemserhat.harmonyhaven.domain.usecase.users

import com.erdemserhat.harmonyhaven.data.network.UserApiService
import com.erdemserhat.harmonyhaven.domain.model.PasswordResetModel
import com.erdemserhat.harmonyhaven.domain.model.ResetPasswordRequest
import javax.inject.Inject

class ResetPasswordUser @Inject constructor(
    private val userApiService: UserApiService
) {
    suspend fun sendMail(email:String){
        val resetPasswordRequest = ResetPasswordRequest(email)
        userApiService.sendPasswordResetMail(resetPasswordRequest)

    }

    suspend fun resetPassword(code:String,newPassword:String){
        val passwordResetModel = PasswordResetModel(code,newPassword)
        userApiService.resetPassword(passwordResetModel)

    }
}