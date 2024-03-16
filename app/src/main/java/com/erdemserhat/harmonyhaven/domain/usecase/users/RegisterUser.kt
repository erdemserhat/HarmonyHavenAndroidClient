package com.erdemserhat.harmonyhaven.domain.usecase.users

import com.erdemserhat.harmonyhaven.data.network.UserApiService
import com.erdemserhat.harmonyhaven.domain.model.rest.client.RequestResultClient
import com.erdemserhat.harmonyhaven.domain.model.rest.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterUser @Inject constructor(
    private val userApiService: UserApiService
) {
    suspend operator fun invoke(newUser: User): Flow<RequestResultClient> = flow {

        emit(RequestResultClient(false,"Loading...",true))
        val response = userApiService.register(newUser)
        val result = response.body()?.result ?:false
        val message = response.body()?.message ?:"En error occurred"

        emit(RequestResultClient(result,message,false))
    }
}