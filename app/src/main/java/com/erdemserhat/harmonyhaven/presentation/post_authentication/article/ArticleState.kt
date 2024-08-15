package com.erdemserhat.harmonyhaven.presentation.post_authentication.article

import android.os.Parcelable
import com.erdemserhat.harmonyhaven.domain.model.rest.ArticleResponseType
import kotlinx.parcelize.Parcelize

data class ArticleState(
    val articleTitle:String="",
    val articleContent:String="",
    val publishDate:String="",
    val category:String?="",
    val imagePath:String="",
    var isLoaded:Boolean = false
)

fun ArticleState.toArticleResponseType(): ArticleResponseType {
    return ArticleResponseType(
        title = this.articleTitle ?: "",  // Title null ise boş string döner
        content = this.articleContent ?: "",  // Content null ise boş string döner
        imagePath = this.imagePath ?: ""  // Image URL null ise boş string döner
    )
}
