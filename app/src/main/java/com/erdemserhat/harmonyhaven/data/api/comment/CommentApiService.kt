package com.erdemserhat.harmonyhaven.data.api.comment

import com.erdemserhat.harmonyhaven.domain.model.rest.Comment
import com.erdemserhat.harmonyhaven.domain.model.rest.CommentRequest
import retrofit2.Response
import retrofit2.http.*

interface CommentApiService {

    @POST("v1/comments")
    suspend fun addComment(
        @Body commentRequest: CommentRequest
    ): Response<Void> //

    @DELETE("v1/comments/{commentId}")
    suspend fun deleteComment(
        @Path("commentId") commentId: Int
    ): Response<Void>

    @GET("v1/comments/post/{postId}")
    suspend fun getCommentsByPostId(
        @Path("postId") postId: Int
    ): Response<List<Comment>>

    @POST("v1/comments/{commentId}/like")
    suspend fun likeComment(
        @Path("commentId") commentId: Int
    ): Response<Void>

    @POST("v1/comments/{commentId}/unlike")
    suspend fun unlikeComment(
        @Path("commentId") commentId: Int
    ): Response<Void>
}
