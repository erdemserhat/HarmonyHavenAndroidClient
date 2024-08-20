package com.erdemserhat.harmonyhaven.presentation.post_authentication.article

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.domain.usecase.article.ArticleUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
            Log.d("dsadsadas",articleID.toString())
                val articleDeferred = async { articleUseCases.getArticleById.executeRequest(articleID) }
                articleDeferred.await()?.let {

                    _articleState.value = _articleState.value.copy(
                        articleTitle = it.title,
                        articleContent = it.content,
                        publishDate = it.publishDate,
                        imagePath = it.imagePath,
                        isLoaded = true

                    )

                }


            } catch (_: Exception) {

            }


        }
    }


}
