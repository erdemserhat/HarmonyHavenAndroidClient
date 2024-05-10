package com.erdemserhat.harmonyhaven.data.local.repository

import com.erdemserhat.harmonyhaven.data.local.dao.ArticleDao
import com.erdemserhat.harmonyhaven.data.local.entities.ArticleEntity
import com.erdemserhat.harmonyhaven.data.local.entities.JwtTokenEntity
import javax.inject.Inject

class ArticleRepository @Inject constructor(
    private val articleDao: ArticleDao
) {

    suspend fun getArticles(): List<ArticleEntity> {
        return articleDao.getArticles()

    }

    suspend fun saveArticle(article: ArticleEntity) {
        articleDao.insertArticle(article)
    }
}