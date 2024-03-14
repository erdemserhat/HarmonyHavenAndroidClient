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

}