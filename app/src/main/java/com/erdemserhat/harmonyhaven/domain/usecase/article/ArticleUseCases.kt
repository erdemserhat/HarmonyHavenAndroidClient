package com.erdemserhat.harmonyhaven.domain.usecase.article

/**
 * Data class that holds all use cases related to articles.
 *
 * @property categories Use case for handling categories.
 * @property getArticleById Use case for fetching an article by its ID.
 * @property getRecentArticles Use case for fetching recent articles.
 * @property getArticlesByCategory Use case for fetching articles by category.
 * @property getAllArticles Use case for fetching all articles.
 * @property getArticles Use case for general article-related operations.
 * @property getCategories Use case for fetching categories.
 */
data class ArticleUseCases(
    val categories: Categories,
    val getArticleById: GetArticleById,
    val getRecentArticles: GetRecentArticles,
    val getArticlesByCategory: GetArticlesByCategory,
    val getAllArticles: GetAllArticles,
    val getArticles: GetArticles,
    val getCategories: GetCategories
)
