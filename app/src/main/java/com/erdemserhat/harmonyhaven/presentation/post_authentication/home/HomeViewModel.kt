package com.erdemserhat.harmonyhaven.presentation.post_authentication.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.domain.model.rest.toArticleResponseType
import com.erdemserhat.harmonyhaven.domain.usecase.article.ArticleUseCases
import com.erdemserhat.harmonyhaven.domain.usecase.user.UserUseCases
import com.erdemserhat.harmonyhaven.presentation.post_authentication.profile.saved_articles.MockSavedArticles
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val articleUseCases: ArticleUseCases,

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


                val categories = categoriesDeferred.await()
                val articles = articlesDeferred.await()

                if (categories == null && articles == null) {
                    Log.d("homepage_tests", "categories and/or articles were null")
                    return@launch
                }

                _homeState.value = _homeState.value.copy(

                    categories = categories!!,
                    categorizedArticles = articles!!.map {
                        it.toArticleResponseType(categories,true)
                    },
                    isCategoryReady = true,
                    isArticleReady = true,
                    recentArticles = articles.take(4)
                        .map { it.toArticleResponseType(categories) },
                    allArticles = articles

                )

                _homeState.value.allArticles.map {

                    Log.d("homepage_tests",it.id.toString() )

                }



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
                if (categoryId == 1) {
                    _homeState.value = _homeState.value.copy(

                        categorizedArticles = _homeState.value.allArticles.map {
                            it.toArticleResponseType(
                                _homeState.value.categories
                            )
                        }
                    )
                } else {
                    _homeState.value = _homeState.value.copy(

                        categorizedArticles = _homeState.value.allArticles.filter { it.categoryId == categoryId }
                            .map { it.toArticleResponseType(_homeState.value.categories) }
                    )

                }


            } catch (_: Exception) {

            }


        }
    }


}
