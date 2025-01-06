package com.erdemserhat.harmonyhaven.domain.model.rest

data class Comment(
    val id: Int,
    var date: String,
    val author: String,
    val content: String,
    val likeCount: Int,
    val isLiked: Boolean,
    var authorProfilePictureUrl: String,
    val hasOwnership: Boolean
)