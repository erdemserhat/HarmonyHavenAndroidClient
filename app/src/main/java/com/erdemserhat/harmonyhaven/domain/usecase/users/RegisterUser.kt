package com.erdemserhat.harmonyhaven.domain.usecase.users

import com.erdemserhat.harmonyhaven.data.network.UserApiService
import com.erdemserhat.harmonyhaven.domain.model.RequestResult
import com.erdemserhat.harmonyhaven.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterUser @Inject constructor(
    private val userApiService: UserApiService
) {
    suspend operator fun invoke(newUser:User): Flow<RequestResult> = flow {

        emit(RequestResult(false,"Loading..."))

        val response = userApiService.register(newUser)
        val result = response.body()?.result ?:false
        val message = response.body()?.message ?:"En error occurred"

        emit(RequestResult(result,message))
    }
}