package com.erdemserhat.harmonyhaven.domain.usecase.users

import com.erdemserhat.harmonyhaven.data.network.UserApiService
import com.erdemserhat.harmonyhaven.domain.model.User
import javax.inject.Inject

class RegisterUser @Inject constructor(
    private val userApiService: UserApiService
) {
    suspend operator fun invoke(newUser:User){
        userApiService.register(newUser)
    }
}