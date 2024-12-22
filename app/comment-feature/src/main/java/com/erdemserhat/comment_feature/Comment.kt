package com.erdemserhat.comment_feature

data class Comment(
    val date:String,
    val author:String,
    val content:String,
    val likeCount:Int,
    val replyCount:Int,
    val isLiked:Boolean,
    val isMainComment:Boolean,
    val authorProfilePictureUrl:String,
)