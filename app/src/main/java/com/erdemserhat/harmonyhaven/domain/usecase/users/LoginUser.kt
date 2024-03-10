package com.erdemserhat.harmonyhaven.domain.usecase.users

import com.erdemserhat.harmonyhaven.data.network.UserApiService
import com.erdemserhat.harmonyhaven.domain.model.RequestResult
import com.erdemserhat.harmonyhaven.domain.model.User
import com.erdemserhat.harmonyhaven.domain.model.UserLogin
import javax.inject.Inject

class LoginUser @Inject constructor(
    private val userApiService: UserApiService
) {
    suspend operator fun invoke(login: UserLogin):RequestResult {
        val response = userApiService.login(login)
        val result = response.body()?.result ?:false
        val message = response.body()?.message ?:"En error occurred"

        return RequestResult(result,message)

    }
}