package com.erdemserhat.harmonyhaven.domain.usecase.users

import com.erdemserhat.harmonyhaven.data.network.UserApiService
import com.erdemserhat.harmonyhaven.domain.model.UserLogin
import javax.inject.Inject

class LoginUser @Inject constructor(
    private val userApiService: UserApiService
) {
    suspend operator fun invoke(login: UserLogin){
        userApiService.login(login)

    }
}