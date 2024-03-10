package com.erdemserhat.harmonyhaven.domain.usecase.users

import com.erdemserhat.harmonyhaven.data.network.UserApiService
import com.erdemserhat.harmonyhaven.domain.model.RequestResult
import com.erdemserhat.harmonyhaven.domain.model.UserUpdateModel
import javax.inject.Inject

class UpdateUser @Inject constructor(
    private val userApiService: UserApiService
) {
    suspend operator fun invoke(userUpdateModel: UserUpdateModel):RequestResult{
        val response = userApiService.updateUser(userUpdateModel)

        val result = response.body()?.result ?:false
        val message = response.body()?.message ?:"En error occurred"

        return RequestResult(result,message)



    }
}