package com.erdemserhat.harmonyhaven.domain.usecase.users

import com.erdemserhat.harmonyhaven.data.network.UserApiService
import com.erdemserhat.harmonyhaven.domain.model.rest.server.RequestResult
import com.erdemserhat.harmonyhaven.domain.model.rest.client.UserUpdateModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateUser @Inject constructor(
    private val userApiService: UserApiService
) {
    suspend operator fun invoke(userUpdateModel: UserUpdateModel): Flow<RequestResult> = flow {
        emit(RequestResult(false,"Loading..."))

        val response = userApiService.updateUser(userUpdateModel)
        val result = response.body()?.result ?:false
        val message = response.body()?.message ?:"En error occurred"

        emit(RequestResult(result,message))



    }
}