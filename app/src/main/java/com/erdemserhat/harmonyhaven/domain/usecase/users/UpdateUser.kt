package com.erdemserhat.harmonyhaven.domain.usecase.users

import com.erdemserhat.harmonyhaven.data.network.UserApiService
import com.erdemserhat.harmonyhaven.domain.model.UserUpdateModel
import javax.inject.Inject

class UpdateUser @Inject constructor(
    private val userApiService: UserApiService
) {
    suspend operator fun invoke(userUpdateModel: UserUpdateModel){
        userApiService.updateUser(userUpdateModel)
    }
}