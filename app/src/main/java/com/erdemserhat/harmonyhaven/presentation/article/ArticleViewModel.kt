package com.erdemserhat.harmonyhaven.presentation.article

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.domain.usecase.article.ArticleUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val articleUseCases: ArticleUseCases
) : ViewModel() {

    private val _articleState = MutableStateFlow(ArticleState())

    val articleState: StateFlow<ArticleState> = _articleState.asStateFlow()

    fun prepareArticle(articleID: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val articleDeferred = async { articleUseCases.getArticleById(articleID) }
                articleDeferred.await()?.let {
                    _articleState.value = _articleState.value.copy(
                        articleTitle = it.title,
                        articleContent = it.content,
                        publishDate = it.publishDate,
                        imagePath = it.imagePath,
                        category = it.category,
                        isLoaded = true

                    )

                }


            } catch (_: Exception) {

            }


        }
    }


}
