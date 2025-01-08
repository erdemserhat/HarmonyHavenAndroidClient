package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.bottom_sheets.comment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.data.api.user.UserInformationApiService
import com.erdemserhat.harmonyhaven.domain.model.rest.Comment
import com.erdemserhat.harmonyhaven.domain.usecase.CommentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val commentUseCase: CommentUseCase,
    private val userInformationApiService: UserInformationApiService,
    private val httpClient:OkHttpClient
) : ViewModel() {
    private var _comments = MutableStateFlow(listOf<Comment>())
    val comments: StateFlow<List<Comment>> = _comments
    var profilePhotoPath: String = ""
    var userName: String = ""
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
                userName = result.name

            }
        }
    }

    fun resetApiCallPool() {
        val calls = httpClient.dispatcher.runningCalls().toList()

        // Check if there are multiple calls in the pool
        if (calls.size > 1) {
            // Keep the last call and cancel the others
            calls.dropLast(1).forEach { call ->
                call.cancel()
            }
        }

        // Log the size of the calls (after the cancellation)
        Log.d("API Call Pool", "Remaining calls: ${calls.size}")
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

    private fun refreshList(postId: Int) {
        viewModelScope.launch {
            Log.d("dasddsadas","job started with $postId")

            val commentsDeferred = async {
                val comments=commentUseCase.getCommentsByPostId(postId).getOrNull()
                comments?.map {
                    if (it.authorProfilePictureUrl == "-")
                        it.authorProfilePictureUrl = defaultPPLink

                }
                comments
            }
            _comments.value = commentsDeferred.await() ?: listOf()
        }

    }


    fun resetList() {
        _comments.value = listOf()
        _isLoading.value = true
    }




    // Her bir yorum için ayrı job referansı
    private val debounceLikeJobs = mutableMapOf<Int, Job>()

    fun likeComment(commentId: Int, postId: Int) {
        viewModelScope.launch {
            // Eğer aynı yorum için tekrar işlem yapılmak istenirse, önceki iş iptal edilsin
            debounceLikeJobs[commentId]?.cancel()

            // Yeni bir iş başlat
            debounceLikeJobs[commentId] = launch {
                // 500 ms'lik gecikme (debounce)
                delay(500)  // 500 ms = 0.5 saniye

                // IO işlerini arka planda yap
                withContext(Dispatchers.IO) {
                    commentUseCase.likeComment(commentId)
                    refreshList(postId)
                }
            }
        }
    }



    // Her bir yorum için ayrı job referansı
    private val debounceUnlikeJobs = mutableMapOf<Int, Job>()

    fun removeLikeFromComment(commentId: Int, postId: Int) {
        viewModelScope.launch {
            // Eğer aynı yorum için tekrar işlem yapılmak istenirse, önceki iş iptal edilsin
            debounceUnlikeJobs[commentId]?.cancel()

            // Yeni bir iş başlat
            debounceUnlikeJobs[commentId] = launch {
                // 500 ms'lik gecikme (debounce)
                delay(500)  // 500 ms = 0.5 saniye

                // IO işlerini arka planda yap
                withContext(Dispatchers.IO) {
                    commentUseCase.unlikeComment(commentId)
                    refreshList(postId)
                }
            }
        }
    }



    fun postComment(postId: Int, comment: String) {
        viewModelScope.launch {
            launch {
                val list = _comments.value.toMutableList()
                list.add(
                    Comment(
                        id = -1,
                        date = "Şimdi",
                        author = userName,
                        content = comment,
                        likeCount = 0,
                        isLiked = false,
                        authorProfilePictureUrl = profilePhotoPath,
                        hasOwnership = true
                    )
                )

                _comments.value = list
            }
            withContext(Dispatchers.IO) {
                commentUseCase.addComment(postId, comment)
                refreshList(postId)
            }

        }
    }

    fun deleteComment(commentId: Int, postId: Int) {
        viewModelScope.launch {
            launch {
                val list = _comments.value.toMutableList()
                val removedComment = list.find { it.id ==commentId }
                list.remove(removedComment)
                _comments.value = list
            }



            withContext(Dispatchers.IO) {
                commentUseCase.deleteComment(commentId)
                refreshList(postId)

            }
        }

    }


}