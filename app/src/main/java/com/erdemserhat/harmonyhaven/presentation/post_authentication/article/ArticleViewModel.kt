package com.erdemserhat.harmonyhaven.presentation.post_authentication.article

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.domain.usecase.article.ArticleUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val articleUseCases: ArticleUseCases
) : ViewModel() {

    private val _articleScreenState = MutableStateFlow(ArticleScreenState())
    val articleScreenState: StateFlow<ArticleScreenState> = _articleScreenState.asStateFlow()

    fun prepareArticle(articleID: Int) {
        viewModelScope.launch {
            try {
                //no need to use async/await because there is already one process here.
                val article = withContext(Dispatchers.IO) {
                    articleUseCases.getArticleById.executeRequest(articleID)
                }
                article?.let {
                    _articleScreenState.value = _articleScreenState.value.copy(
                        articleTitle = it.title,
                        articleContent = it.content,
                        publishDate = it.publishDate,
                        imagePath = it.imagePath,
                        isLoaded = true,
                        slug = it.slug,
                        id = it.id
                    )
                }
            } catch (e: Exception) {
                Log.e("ArticleViewModelError", e.message ?: "Unknown error")
            }
        }
    }
}
