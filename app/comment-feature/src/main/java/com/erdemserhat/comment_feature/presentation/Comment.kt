package com.erdemserhat.comment_feature.presentation

data class Comment(
    val id:Int,
    val date:String,
    val author:String,
    val content:String,
    val likeCount:Int,
    val isLiked:Boolean,
    val isMainComment:Boolean,
    val authorProfilePictureUrl:String,
)