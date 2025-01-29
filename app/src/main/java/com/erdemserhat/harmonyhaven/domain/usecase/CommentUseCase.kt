package com.erdemserhat.harmonyhaven.domain.usecase

import com.erdemserhat.harmonyhaven.data.api.comment.CommentApiService
import com.erdemserhat.harmonyhaven.domain.model.rest.Comment
import com.erdemserhat.harmonyhaven.domain.model.rest.CommentRequest
import javax.inject.Inject

class CommentUseCase @Inject constructor(
    private val commentApiService: CommentApiService
) {

    // Add a new comment
    suspend fun addComment(postId: Int, comment: String): Result<Unit> {
        return try {
            val request = CommentRequest(postId, comment)
            val response = commentApiService.addComment(request)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to add comment: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Delete a comment
    suspend fun deleteComment(commentId: Int): Result<Unit> {
        return try {
            val response = commentApiService.deleteComment(commentId)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to delete comment: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Get comments by post ID
    suspend fun getCommentsByPostId(postId: Int): Result<List<Comment>> {
        return try {
            val response = commentApiService.getCommentsByPostId(postId)
            if (response.isSuccessful) {
                response.body()?.let { comments ->
                    Result.success(comments)
                } ?: Result.failure(Exception("Empty response"))
            } else {
                Result.failure(Exception("Failed to fetch comments: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Like a comment
    suspend fun likeComment(commentId: Int): Result<Unit> {

        return try {
            val response = commentApiService.likeComment(commentId)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to like comment: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Unlike a comment
    suspend fun unlikeComment(commentId: Int): Result<Unit> {
        return try {
            val response = commentApiService.unlikeComment(commentId)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to unlike comment: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
