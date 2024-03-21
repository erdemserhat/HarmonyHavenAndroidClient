package com.erdemserhat.harmonyhaven.domain.usecase.article

data class ArticleUseCases(
    val categories: Categories,
    val getArticleById: GetArticleById,
    val getRecentArticles: GetRecentArticles
)
