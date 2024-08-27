package com.erdemserhat.harmonyhaven.presentation.post_authentication.home

import com.erdemserhat.harmonyhaven.domain.model.rest.Article
import com.erdemserhat.harmonyhaven.domain.model.rest.ArticlePresentableUIModel
import com.erdemserhat.harmonyhaven.domain.model.rest.Category

data class HomeState(
    var categories: List<Category> = listOf(),
    var isCategoryReady:Boolean = false,
    var categorizedArticles:List<ArticlePresentableUIModel> = listOf(),
    var recentArticles:List<ArticlePresentableUIModel> = listOf(),
    var isArticleReady:Boolean = false,
    var allArticles:List<Article> = listOf(),
)
