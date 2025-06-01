package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.bottom_sheets.comment

import android.util.Log
import androidx.collection.emptyLongList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.erdemserhat.harmonyhaven.data.api.user.UserInformationApiService
import com.erdemserhat.harmonyhaven.domain.model.rest.Comment
import com.erdemserhat.harmonyhaven.domain.usecase.CommentUseCase
import com.erdemserhat.harmonyhaven.util.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import javax.inject.Inject
import kotlin.properties.Delegates
import kotlin.system.measureTimeMillis

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val commentUseCase: CommentUseCase,
    private val userInformationApiService: UserInformationApiService,
    private val httpClient: OkHttpClient
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

    private val _lastPostId = MutableStateFlow(-1)
    val lastPostId: StateFlow<Int> = _lastPostId
    private var cachedCommentList: List<Comment> = listOf()

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    delay(500)
                    val response = NetworkUtils.retryWithBackoff {
                        userInformationApiService.getUserInformation()
                    }

                    if (response.isSuccessful) {
                        val result = response.body()
                        if (result != null) {
                            profilePhotoPath = if (result.profilePhotoPath == "-") defaultPPLink else result.profilePhotoPath
                            userId = result.id
                            userName = result.name
                        } else {
                            Log.e("CommentViewModel", "Body is null despite successful response")
                        }
                    } else {
                        Log.e("CommentViewModel", "Unsuccessful response: ${response.code()} ${response.errorBody()?.string()}")
                    }

                } catch (e: Exception) {
                    Log.e("CommentViewModel", "Error fetching user info after retries", e)
                }
            }

        }

    }

    fun setLastPostId(postId: Int) {
        cachedCommentList = _comments.value
        _lastPostId.value = postId
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
            val elapsedTime = measureTimeMillis {
                val comments = commentUseCase.getCommentsByPostId(postId).getOrNull()
                comments?.map {
                    if (it.authorProfilePictureUrl == "-")
                        it.authorProfilePictureUrl = defaultPPLink
                }

                _comments.value = comments ?: listOf()
            }

            if (elapsedTime<750){
                delay(750- elapsedTime)
                _isLoading.value = false
            }else{
                _isLoading.value = false

            }
        }
    }

    private fun refreshList(postId: Int) {
        viewModelScope.launch {
            Log.d("dasddsadas", "job started with $postId")


            val commentsDeferred = async {
                val comments = commentUseCase.getCommentsByPostId(postId).getOrNull()
                comments?.map {
                    if (it.authorProfilePictureUrl == "-")
                        it.authorProfilePictureUrl = defaultPPLink

                }
                comments
            }
            _comments.value = commentsDeferred.await() ?: listOf()

            setLastPostId(postId)
        }

    }


    fun resetList() {
        _isLoading.value = true
        _comments.value = listOf()

    }

    fun loadFromCache() {

        _comments.value = cachedCommentList
        Log.d("dasddsadas", cachedCommentList.toString())

        _isLoading.value = false

    }



    // Her bir yorum için ayrı job referansı
    private val debounceLikeJobs = mutableMapOf<Int, Job>()
    private val debounceDelayJobs = mutableMapOf<Int, Job>()

    fun likeComment(commentId: Int, postId: Int) {
        _comments.value = _comments.value.map { comment ->
            val previousLikeCondition = if (comment.id == commentId) comment.isLiked else false
            if (comment.id == commentId) comment.copy(
                isLiked = true,
                likeCount = if (!previousLikeCondition) comment.likeCount+1 else comment.likeCount
            )
            else comment
        }
        viewModelScope.launch {
            // Eğer aynı yorum için tekrar işlem yapılmak istenirse, önceki iş iptal edilsin
            debounceLikeJobs[commentId]?.cancel()

            // Yeni bir iş başlat
            debounceLikeJobs[commentId] = launch {

                debounceDelayJobs[commentId] = launch {
                    delay(1000)  // 500 ms = 0.5 saniye
                }
                debounceDelayJobs[commentId]?.join()


                // IO işlerini arka planda yap
                Log.d("fsdfdsf", "network call")
                withContext(Dispatchers.IO) {
                    Log.d("TRACE_123","API REQUEST SENT")
                    commentUseCase.likeComment(commentId)
                    //refreshList(postId)
                }
            }


        }
    }

    fun commitApiCallsWithoutDelay() {

        viewModelScope.launch {
            launch {
                debounceDelayJobs.forEach { (id, job) ->  // Key ve Job ayrı ayrı alınır
                    job.cancel()  // Her bir işi iptal et
                }

            }.join()

            launch {

                debounceDelayJobs2.forEach { (id, job) ->  // Key ve Job ayrı ayrı alınır
                    job.cancel()  // Her bir işi iptal et
                }

            }.join()

            launch {


            }.join()


        }


    }


    private val debounceDelayJobs2 = mutableMapOf<Int, Job>()

    fun removeLikeFromComment(commentId: Int, postId: Int) {
        _comments.value = _comments.value.map { comment ->
            if (comment.id == commentId) comment.copy(
                isLiked = false,
                likeCount = comment.likeCount - 1
            ) else comment
        }
        viewModelScope.launch {
            // Eğer aynı yorum için tekrar işlem yapılmak istenirse, önceki iş iptal edilsin
            debounceLikeJobs[commentId]?.cancel()

            // Yeni bir iş başlat
            debounceLikeJobs[commentId] = launch {
                debounceDelayJobs2[commentId] = launch {
                    delay(1000)  // 500 ms = 0.5 saniye
                }
                debounceDelayJobs2[commentId]?.join()

                // IO işlerini arka planda yap
                Log.d("fsdfdsf", "network call")

                withContext(Dispatchers.IO) {
                    Log.d("TRACE_123","API REQUEST SENT")
                    commentUseCase.unlikeComment(commentId)
                    //refreshList(postId)
                }
            }
        }
    }

    fun logEvent(){

    }


    fun postComment(postId: Int, comment: String) {
        viewModelScope.launch {
            launch {
                val list = _comments.value.toMutableList()
                list.add(
                    Comment(
                        id = -1,
                        date = "paylaşılıyor",
                        author = userName,
                        content = comment,
                        likeCount = 0,
                        isLiked = false,
                        authorProfilePictureUrl = profilePhotoPath,
                        hasOwnership = true
                    )
                )

                _comments.value = list
            }.join()
            withContext(Dispatchers.IO) {
                commentUseCase.addComment(postId, comment)
                refreshList(postId)
            }

        }
    }

    fun deleteComment(commentId: Int, postId: Int) {
        viewModelScope.launch {
            launch {
                val list = _comments.value.toMutableList().map { comment->
                    if(comment.id == commentId)  comment.copy(id=-1) else comment
                }
                _comments.value = list
                delay(300)
                _comments.value = _comments.value.filter {comment->
                    comment.id!=-1
                }
            }

            withContext(Dispatchers.IO) {
                commentUseCase.deleteComment(commentId)
                //refreshList(postId)

            }
        }

    }


}

