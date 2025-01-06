package com.erdemserhat.harmonyhaven.domain.model.rest

data class CommentRequest(
    val postId: Int,
    val comment: String
)