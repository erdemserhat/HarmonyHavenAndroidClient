package com.erdemserhat.harmonyhaven.presentation.post_authentication.article

data class ArticleState(
    val articleTitle:String="",
    val articleContent:String="",
    val publishDate:String="",
    val category:String?="",
    val imagePath:String="",
    var isLoaded:Boolean = false
)