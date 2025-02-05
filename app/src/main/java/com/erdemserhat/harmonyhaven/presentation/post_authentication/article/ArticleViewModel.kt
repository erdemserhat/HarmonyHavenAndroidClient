package com.erdemserhat.harmonyhaven.presentation.post_authentication.article

import android.content.SharedPreferences
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
import javax.inject.Named


@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val articleUseCases: ArticleUseCases,
    @Named("ArticleReadingPreferences")
    private val articleReadingPreferences: SharedPreferences
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

    fun getSavedArticleReadingPreferences():ArticleReadingPreferences{
        val fontSize = articleReadingPreferences.getInt("fontSize",ArticleScreenConstants.DEFAULT_FONT_SIZE)
        val selectedFontStyle = articleReadingPreferences.getInt("selectedFontStyle",0)
        val selectedBackgroundColorVariance = articleReadingPreferences.getInt("selectedBackgroundColorVariance",0)

        return ArticleReadingPreferences(
            fontSize,selectedFontStyle,selectedBackgroundColorVariance
        )


    }

    fun saveArticleReadingPreferences(preferences: ArticleReadingPreferences){
        viewModelScope.launch {
            articleReadingPreferences.edit()
                .putInt("fontSize",preferences.fontSize)
                .putInt("selectedFontStyle",preferences.selectedFontStyle)
                .putInt("selectedBackgroundColorVariance",preferences.selectedBackgroundColorVariance)
                .apply()

        }


    }
}

data class ArticleReadingPreferences(
    val fontSize:Int,
    val selectedFontStyle: Int,
    val selectedBackgroundColorVariance: Int
)


