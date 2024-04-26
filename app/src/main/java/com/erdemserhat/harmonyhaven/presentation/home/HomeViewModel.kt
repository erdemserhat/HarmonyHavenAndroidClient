package com.erdemserhat.harmonyhaven.presentation.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.domain.model.rest.toArticleResponseType
import com.erdemserhat.harmonyhaven.domain.usecase.article.ArticleUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val articleUseCases: ArticleUseCases

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
                    articles = articles!!.map { it.toArticleResponseType(categories) },
                    isCategoryReady = true,
                    isArticleReady = true,
                    recentArticles = articles.take(4)
                        .map { it.toArticleResponseType(categories) },
                    allArticles = articles

                )
                Log.d("homepage_tests", "request operation is successful")
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
                _homeState.value = _homeState.value.copy(
                    articles = _homeState.value.allArticles.filter { it.categoryId == categoryId }
                        .map { it.toArticleResponseType(_homeState.value.categories) }
                )
                Log.d("qazq", _homeState.value.articles.toString())

            } catch (_: Exception) {

            }


        }
    }


}
