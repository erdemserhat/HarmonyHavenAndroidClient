package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.bottom_sheets.comment

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.erdemserhat.harmonyhaven.data.api.user.UserInformationApiService
import com.erdemserhat.harmonyhaven.domain.model.rest.Comment
import com.erdemserhat.harmonyhaven.domain.usecase.CommentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.properties.Delegates
import java.time.LocalDateTime
import java.time.Duration

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val commentUseCase: CommentUseCase,
    private val userInformationApiService: UserInformationApiService
) : ViewModel() {
    private var _comments = MutableStateFlow(listOf<Comment>())
    val comments: StateFlow<List<Comment>> = _comments
    var profilePhotoPath: String = ""
    var userId by Delegates.notNull<Int>()
    private val defaultPPLink =
        "https://static.vecteezy.com/system/resources/thumbnails/035/857/753/small_2x/people-face-avatar-icon-cartoon-character-png.png"

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = userInformationApiService.getUserInformation().body()!!
                profilePhotoPath =
                    if (result.profilePhotoPath == "-") defaultPPLink else result.profilePhotoPath
                userId = result.id

            }
        }
    }


    fun loadComments(postId: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            val comments = commentUseCase.getCommentsByPostId(postId).getOrNull()
            comments?.map {
                if (it.authorProfilePictureUrl == "-")
                    it.authorProfilePictureUrl = defaultPPLink
            }

            _comments.value = comments ?: listOf()
            _isLoading.value = false
        }
    }

    fun refreshList(postId: Int) {
        viewModelScope.launch {
            val comments = commentUseCase.getCommentsByPostId(postId).getOrNull()
            comments?.map {
                if (it.authorProfilePictureUrl == "-")
                    it.authorProfilePictureUrl = defaultPPLink

            }

            _comments.value = comments ?: listOf()
        }

    }



    fun resetList() {
        _comments.value = listOf()
        _isLoading.value = true
    }

    fun likeComment(commentId: Int, postId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                commentUseCase.likeComment(commentId)
                refreshList(postId)

            }

        }

    }

    fun removeLikeFromComment(commentId: Int, postId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                commentUseCase.unlikeComment(commentId)
                refreshList(postId)


            }

        }

    }

    fun postComment(postId: Int, comment: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                commentUseCase.addComment(postId, comment)
                refreshList(postId)
            }

        }
    }

    fun deleteComment(commentId: Int, postId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                commentUseCase.deleteComment(commentId)
                refreshList(postId)

            }
        }

    }


}