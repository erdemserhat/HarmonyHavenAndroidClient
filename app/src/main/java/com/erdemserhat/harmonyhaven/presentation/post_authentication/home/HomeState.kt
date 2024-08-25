package com.erdemserhat.harmonyhaven.presentation.post_authentication.home

import com.erdemserhat.harmonyhaven.domain.model.rest.Article
import com.erdemserhat.harmonyhaven.domain.model.rest.ArticleResponseType
import com.erdemserhat.harmonyhaven.domain.model.rest.Category
import com.erdemserhat.harmonyhaven.domain.model.rest.toArticleResponseType

data class HomeState(
    var categories: List<Category> = listOf(),
    var isCategoryReady:Boolean = false,
    var categorizedArticles:List<ArticleResponseType> = listOf(),
    var recentArticles:List<ArticleResponseType> = listOf(),
    var isArticleReady:Boolean = false,
    var allArticles:List<Article> = listOf(),
    var authStatus:Int=1,
)
