package com.erdemserhat.comment_feature.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.erdemserhat.comment_feature.Comment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor() : ViewModel() {
    private var _comments = MutableStateFlow(listOf<Comment>())
    val comments: StateFlow<List<Comment>> = _comments

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading


    fun loadComments() {
        _isLoading.value = true
        val mockComments = listOf(
            Comment(
                date = "2024-12-22",
                author = "John Doe",
                content = "This is an amazing post! Keep up the great work.",
                likeCount = 124,
                replyCount = 5,
                isLiked = true,
                isMainComment = true,
                authorProfilePictureUrl = "https://erdemserhat.com/wp-content/uploads/2024/06/photo_2024-06-20_14-25-45.jpg"
            ),
            Comment(
                date = "2024-12-21",
                author = "Jane Smith",
                content = "I completely agree with your points. Very insightful!",
                likeCount = 89,
                replyCount = 2,
                isLiked = false,
                isMainComment = true,
                authorProfilePictureUrl = "https://erdemserhat.com/wp-content/uploads/2024/06/photo_2024-06-20_14-25-45.jpg"
            ),
            Comment(
                date = "2024-12-21",
                author = "Michael Brown",
                content = "Could you elaborate more on this topic? It's intriguing.",
                likeCount = 12,
                replyCount = 0,
                isLiked = false,
                isMainComment = false, // Alt bir yorum
                authorProfilePictureUrl = "https://erdemserhat.com/wp-content/uploads/2024/06/photo_2024-06-20_14-25-45.jpg"
            ),
            Comment(
                date = "2024-12-20",
                author = "Emily White",
                content = "I learned a lot from this post. Thanks for sharing!",
                likeCount = 72,
                replyCount = 3,
                isLiked = true,
                isMainComment = true,
                authorProfilePictureUrl = "https://erdemserhat.com/wp-content/uploads/2024/06/photo_2024-06-20_14-25-45.jpg"
            ),
            Comment(
                date = "2024-12-20",
                author = "David Black",
                content = "Interesting perspective. I hadn't thought of it that way.",
                likeCount = 56,
                replyCount = 1,
                isLiked = false,
                isMainComment = false, // Alt bir yorum
                authorProfilePictureUrl = "https://erdemserhat.com/wp-content/uploads/2024/06/photo_2024-06-20_14-25-45.jpg"
            )
        )
        viewModelScope.launch {
            delay(5_000)
            _comments.value = mockComments
            _isLoading.value = false
        }


    }


}