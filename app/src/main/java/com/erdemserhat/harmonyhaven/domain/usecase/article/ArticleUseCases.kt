package com.erdemserhat.harmonyhaven.domain.usecase.article

data class ArticleUseCases(
    val categories: Categories,
    val getArticleById: GetArticleById,
    val getRecentArticles: GetRecentArticles,
    val getArticlesByCategory: GetArticlesByCategory,
    val getAllArticles: GetAllArticles,
    val getArticles: GetArticles,
    val getCategories: GetCategories
)
