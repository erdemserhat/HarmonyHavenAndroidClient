package com.erdemserhat.harmonyhaven.presentation.post_authentication.enneagram.profil

import com.erdemserhat.harmonyhaven.data.api.enneagram.CheckingTestResultDto
import com.erdemserhat.harmonyhaven.data.api.enneagram.EnneagramTestResultDetailedDto

data class UserProfileState(
    var shouldResetScrollState:Boolean = false,
    var isLoading:Boolean = false,
    val result: CheckingTestResultDto? = null,
    val username:String? = null,
    val userProfilePicturePath:String? = null
)
