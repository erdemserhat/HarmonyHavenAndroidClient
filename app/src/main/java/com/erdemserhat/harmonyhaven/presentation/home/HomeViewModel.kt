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
                /*

                //network requests
                val categoriesDeferred = async {
                    articleUseCases.categories.invoke()
                }

                val allArticlesDeferred = async {
                    articleUseCases.getAllArticles()
                }

                val categoryResult = categoriesDeferred.await()
                val allArticles = allArticlesDeferred.await()



                _homeState.value = _homeState.value.copy(
                    isCategoryReady = true,
                    categories = categoryResult,
                    isArticleReady = true,
                    allArticles = allArticles,
                    recentArticles = allArticles.takeLast(4).map { it.toArticleResponseType(_homeState.value.categories) }
                )

                 */


            } catch (e: Exception) {
                // Hata durumunda gerekli işlemler
                Log.e("erdem3451", "Hata oluştu: ${e.message}")
            }

            //Log.d("erdem3451", homeState.toString())
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
