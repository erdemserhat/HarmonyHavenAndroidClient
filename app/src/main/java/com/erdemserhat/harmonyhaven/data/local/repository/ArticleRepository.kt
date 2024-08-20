package com.erdemserhat.harmonyhaven.data.local.repository

import com.erdemserhat.harmonyhaven.data.local.dao.ArticleDao
import com.erdemserhat.harmonyhaven.data.local.entities.ArticleEntity
import javax.inject.Inject

/**
 * Repository class for managing article data.
 *
 * This class provides methods to interact with the `ArticleDao` for performing database operations related to articles.
 * It acts as an intermediary between the data layer (DAO) and the rest of the application.
 *
 * @constructor Creates an instance of [ArticleRepository] with the given [ArticleDao] dependency.
 * @property articleDao The DAO (Data Access Object) used to perform operations on the 'articles' table.
 */
class ArticleRepository @Inject constructor(
    private val articleDao: ArticleDao
) {

    /**
     * Retrieves a list of all articles from the database.
     *
     * This method fetches all article records stored in the 'articles' table.
     *
     * @return A list of [ArticleEntity] objects representing the articles.
     */
    suspend fun getArticles(): List<ArticleEntity> {
        return articleDao.getArticles()
    }

    /**
     * Saves a new article to the database.
     *
     * This method inserts or updates the given article record in the 'articles' table.
     * If the article already exists (based on its primary key), it will be replaced.
     *
     * @param article The [ArticleEntity] object to be inserted or updated in the database.
     */
    suspend fun saveArticle(article: ArticleEntity) {
        articleDao.insertArticle(article)
    }
}
