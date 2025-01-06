package com.erdemserhat.comment_feature.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class CommentViewModel @Inject constructor() : ViewModel() {
    private var _comments = MutableStateFlow(listOf<Comment>())
    val comments: StateFlow<List<Comment>> = _comments

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading


    fun loadComments(quoteId:Int=1) {
        _isLoading.value = true
        val mockComments = listOf(
            Comment(
                id=1,
                date = "2024-12-22",
                author = "John Doe",
                content = "This is an amazing post! Keep up the great work.",
                likeCount = 124,
                isLiked = true,
                isMainComment = true,
                authorProfilePictureUrl = "https://erdemserhat.com/wp-content/uploads/2024/06/photo_2024-06-20_14-25-45.jpg"
            ),
            Comment(
                id=2,
                date = "2024-12-21",
                author = "Jane Smith",
                content = "I completely agree with your points. Very insightful!",
                likeCount = 89,
                isLiked = false,
                isMainComment = true,
                authorProfilePictureUrl = "https://erdemserhat.com/wp-content/uploads/2024/06/photo_2024-06-20_14-25-45.jpg"
            ),
            Comment(
                id=3,
                date = "2024-12-21",
                author = "Michael Brown",
                content = "Could you elaborate more on this topic? It's intriguing.",
                likeCount = 12,
                isLiked = false,
                isMainComment = false, // Alt bir yorum
                authorProfilePictureUrl = "https://erdemserhat.com/wp-content/uploads/2024/06/photo_2024-06-20_14-25-45.jpg"
            ),
            Comment(
                id=4,
                date = "2024-12-20",
                author = "Emily White",
                content = "I learned a lot from this post. Thanks for sharing!",
                likeCount = 72,
                isLiked = true,
                isMainComment = true,
                authorProfilePictureUrl = "https://erdemserhat.com/wp-content/uploads/2024/06/photo_2024-06-20_14-25-45.jpg"
            ),
            Comment(
                id=5,
                date = "2024-12-20",
                author = "David Black",
                content = "Interesting perspective. I hadn't thought of it that way.",
                likeCount = 56,
                isLiked = false,
                isMainComment = false, // Alt bir yorum
                authorProfilePictureUrl = "https://erdemserhat.com/wp-content/uploads/2024/06/photo_2024-06-20_14-25-45.jpg"
            ),
            Comment(
                id=6,
                date = "2024-12-20",
                author = "David Black",
                content = "Interesting perspective. I hadn't thought of it that way.",
                likeCount = 56,
                isLiked = false,
                isMainComment = false, // Alt bir yorum
                authorProfilePictureUrl = "https://erdemserhat.com/wp-content/uploads/2024/06/photo_2024-06-20_14-25-45.jpg"
            ),
            Comment(
                id=7,
                date = "2024-12-20",
                author = "David Black",
                content = "Interesting perspective. I hadn't thought of it that way.",
                likeCount = 56,
                isLiked = false,
                isMainComment = false, // Alt bir yorum
                authorProfilePictureUrl = "https://erdemserhat.com/wp-content/uploads/2024/06/photo_2024-06-20_14-25-45.jpg"
            ),
            Comment(
                id=8,
                date = "2024-12-20",
                author = "David Black",
                content = "Interesting perspective. I hadn't thought of it that way.",
                likeCount = 56,
                isLiked = false,
                isMainComment = false, // Alt bir yorum
                authorProfilePictureUrl = "https://erdemserhat.com/wp-content/uploads/2024/06/photo_2024-06-20_14-25-45.jpg"
            ),
            Comment(
                id=9,
                date = "2024-12-20",
                author = "David Black",
                content = "Interesting perspective. I hadn't thought of it that way.",
                likeCount = 56,
                isLiked = false,
                isMainComment = false, // Alt bir yorum
                authorProfilePictureUrl = "https://erdemserhat.com/wp-content/uploads/2024/06/photo_2024-06-20_14-25-45.jpg"
            )

        )

        viewModelScope.launch {
            delay(1_000)
            //make api call
            _comments.value = mockComments
            _isLoading.value = false
        }
    }

    fun likeComment(id:Int){
        viewModelScope.launch {
            withContext(Dispatchers.IO){

            }

        }

    }

    fun removeLikeFromComment(id:Int){
        viewModelScope.launch {
            withContext(Dispatchers.IO){

            }

        }

    }

    fun postComment(postId:Int,content:String){
        viewModelScope.launch {
            withContext(Dispatchers.IO){

            }

        }
    }

    fun deleteComment(id:Int){
        viewModelScope.launch {
            withContext(Dispatchers.IO){

            }
        }

    }


}