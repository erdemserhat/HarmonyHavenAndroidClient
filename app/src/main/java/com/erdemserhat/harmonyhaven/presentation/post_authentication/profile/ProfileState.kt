package com.erdemserhat.harmonyhaven.presentation.post_authentication.profile

data class ProfileState(
    val isLoading:Boolean = false,
    val activeDays:Int =0,
    val likedCount:Int =0,
    val messageCount:Int =0,

)
