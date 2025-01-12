package com.erdemserhat.harmonyhaven.domain.model.rest

data class Comment(
    var id: Int,
    var date: String,
    val author: String,
    val content: String,
    var likeCount: Int,
    var isLiked: Boolean,
    var authorProfilePictureUrl: String,
    val hasOwnership: Boolean
)