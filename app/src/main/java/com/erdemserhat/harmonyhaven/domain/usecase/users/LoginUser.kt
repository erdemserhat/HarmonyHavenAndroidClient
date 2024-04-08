package com.erdemserhat.harmonyhaven.domain.usecase.users

import com.erdemserhat.harmonyhaven.data.network.UserApiService
import com.erdemserhat.harmonyhaven.domain.model.rest.client.RequestResultClient
import com.erdemserhat.harmonyhaven.domain.model.rest.client.UserLogin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUser @Inject constructor(
    private val userApiService: UserApiService
) {
    suspend operator fun invoke(login: UserLogin): Flow<RequestResultClient> = flow {
        emit(RequestResultClient(false,"Loading...",true))
        /*
        val response = userApiService.login(login)
        val result = response.body()?.result ?:false
        val message = response.body()?.message ?:"Error..!"

        emit(RequestResultClient(result,message,false))

         */

    }
}