package com.erdemserhat.harmonyhaven.presentation.post_authentication.article

import com.erdemserhat.harmonyhaven.domain.model.rest.ArticlePresentableUIModel

data class ArticleScreenState(
    val articleTitle:String="",
    val articleContent:String="",
    val publishDate:String="",
    val category:String?="",
    val imagePath:String="",
    var isLoaded:Boolean = false
)

fun ArticleScreenState.toArticleResponseType(): ArticlePresentableUIModel {
    return ArticlePresentableUIModel(
        title = this.articleTitle,
        content = this.articleContent,
        imagePath = this.imagePath
    )
}
