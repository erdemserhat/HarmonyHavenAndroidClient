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
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val articleUseCases: ArticleUseCases

) : ViewModel() {
    private val _homeState = mutableStateOf(HomeState())

    val homeState: State<HomeState> = _homeState

    init {
        viewModelScope.launch(Dispatchers.IO) {

            val articlesDeferred = async {
                articleUseCases.articles.invoke()
            }


            val categoriesDeferred = async {
                articleUseCases.categories.invoke()
            }


            try {
                val categoryResult = categoriesDeferred.await()
                val articleResult = articlesDeferred.await()

                _homeState.value = _homeState.value.copy(
                    isCategoryReady = true,
                    categories = categoryResult,
                    isArticleReady = true,
                    articles = articleResult
                )
            } catch (e: Exception) {
                // Hata durumunda gerekli işlemler
                Log.e("erdem3451", "Hata oluştu: ${e.message}")
            }

            Log.d("erdem3451", homeState.toString())
        }
    }
}
