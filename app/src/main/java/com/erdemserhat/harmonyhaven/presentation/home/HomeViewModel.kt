package com.erdemserhat.harmonyhaven.presentation.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
                //network reequests
                val categoriesDeferred = async {
                    articleUseCases.categories.invoke()
                }

                val recentArticlesDeferred = async {
                    articleUseCases.getRecentArticles(4)
                }


                val categoryResult = categoriesDeferred.await()
                val recentArticlesResult = recentArticlesDeferred.await() ?: listOf()

                _homeState.value = _homeState.value.copy(
                    isCategoryReady = true,
                    categories = categoryResult,
                    isArticleReady = true,
                    articles = recentArticlesResult
                )
            } catch (e: Exception) {
                // Hata durumunda gerekli işlemler
                Log.e("erdem3451", "Hata oluştu: ${e.message}")
            }

            //Log.d("erdem3451", homeState.toString())
        }
    }


    fun getArticlesByCategoryId(categoryId:Int){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val articlesWithCurrentCategory = async { articleUseCases.getArticlesByCategory(categoryId) }
                val recentArticlesResult = articlesWithCurrentCategory.await() ?: listOf()
                _homeState.value = _homeState.value.copy(
                    isArticleReady = true,
                    articles = recentArticlesResult
                )
                Log.d("erdem3451",_homeState.value.toString())


            }catch (_:Exception){

            }


        }
    }
}
