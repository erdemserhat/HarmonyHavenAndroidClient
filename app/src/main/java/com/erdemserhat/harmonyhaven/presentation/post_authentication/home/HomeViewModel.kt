package com.erdemserhat.harmonyhaven.presentation.post_authentication.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.data.local.entities.toArticle
import com.erdemserhat.harmonyhaven.data.local.entities.toCategory
import com.erdemserhat.harmonyhaven.data.local.repository.ArticleRepository
import com.erdemserhat.harmonyhaven.data.local.repository.CategoryRepository
import com.erdemserhat.harmonyhaven.domain.model.rest.Article
import com.erdemserhat.harmonyhaven.domain.model.rest.Category
import com.erdemserhat.harmonyhaven.domain.model.rest.toArticleEntity
import com.erdemserhat.harmonyhaven.domain.model.rest.toArticleResponseType
import com.erdemserhat.harmonyhaven.domain.model.rest.toCategoryEntity
import com.erdemserhat.harmonyhaven.domain.usecase.article.ArticleUseCases
import com.erdemserhat.harmonyhaven.domain.usecase.user.UserUseCases
import com.erdemserhat.harmonyhaven.presentation.post_authentication.profile.saved_articles.MockSavedArticles
import com.erdemserhat.harmonyhaven.presentation.post_authentication.profile.saved_articles.MockSavedArticles.mockArticle
import com.google.android.gms.tasks.Task
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val articleUseCases: ArticleUseCases,
    //private val categoryRepository: CategoryRepository,
    //private val articleRepository: ArticleRepository,
    private val userUseCases: UserUseCases

) : ViewModel() {
    private val _homeState = MutableStateFlow(HomeState())

    val homeState: StateFlow<HomeState> = _homeState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val categoriesDeferred = async {
                    articleUseCases.getCategories.executeRequest()
                }

                val articlesDeferred = async {
                    articleUseCases.getArticles.executeRequest()
                }

                val authStatusDeferred = async {
                    userUseCases.checkUserAuthenticationStatus.executeRequest()
                }

                val categories = categoriesDeferred.await()
                val articles = articlesDeferred.await()
                val authStatus = authStatusDeferred.await()
                Log.d("authStatusTest",authStatus.toString())

                if(authStatus!=1){
                    _homeState.value = _homeState.value.copy(authStatus=2)
                    return@launch
                }

                updateFcmToken()


                if (categories == null && articles == null) {
                    Log.d("homepage_tests", "categories and/or articles were null")
                    return@launch
                }

                _homeState.value = _homeState.value.copy(

                    categories = categories!!,
                    categorizedArticles = articles!!.map {
                        it.id=-1
                        it.toArticleResponseType(categories) },
                    isCategoryReady = true,
                    isArticleReady = true,
                    recentArticles = articles.take(4)
                        .map { it.toArticleResponseType(categories) },
                    allArticles = articles

                )
                Log.d("homepage_tests", "request operation is successful")
                //for mocking//////
                MockSavedArticles.setData(_homeState.value.categorizedArticles)
                return@launch


            } catch (e: Exception) {
                //Handle the error process
                Log.d("homepage_tests", "request operation is successful")

            }

        }
    }


    fun getArticlesByCategoryId(categoryId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (categoryId==1){
                    _homeState.value = _homeState.value.copy(

                        categorizedArticles = _homeState.value.allArticles.map { it.toArticleResponseType(_homeState.value.categories) }
                    )
                }
                else{
                    _homeState.value = _homeState.value.copy(

                        categorizedArticles = _homeState.value.allArticles.filter { it.categoryId == categoryId }
                            .map { it.toArticleResponseType(_homeState.value.categories) }
                    )

                }



            } catch (_: Exception) {

            }


        }
    }

    private fun updateFcmToken() {
        viewModelScope.launch(Dispatchers.IO) {
            FirebaseMessaging.getInstance().subscribeToTopic("everyone")
                .addOnCompleteListener { task: Task<Void?> ->
                    Log.d("erdem", task.result.toString())
                    if (task.isSuccessful) {
                        Log.d("spec1", "Successfully subscribed to topic")
                    } else {
                        Log.e("spec1", "Failed to subscribe to topic", task.exception)
                    }
                }
            val localToken = Firebase.messaging.token.await()
            //send your fcm id to server
            Log.d("erdem1212", localToken.toString())
            val fcmToken = localToken.toString()

            val response = userUseCases.fcmEnrolment.executeRequest(fcmToken)

            Log.d("fcmtestResults", response.message)


        }

    }


}
