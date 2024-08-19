package com.erdemserhat.harmonyhaven.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.erdemserhat.harmonyhaven.data.local.entities.ArticleEntity


@Dao
interface ArticleDao {

    /**
     * Retrieves all articles from the database.
     *
     * This method performs a query to fetch all articles stored in the 'articles' table.
     * It returns a list of [ArticleEntity] objects representing the articles.
     *
     * @return A list of [ArticleEntity] objects containing all articles from the database.
     */
    @Query("SELECT * FROM articles")
    suspend fun getArticles(): List<ArticleEntity>

    /**
     * Inserts a new article into the database or updates an existing article if it already exists.
     *
     * This method performs an insert operation on the 'articles' table. If an article with the same
     * primary key already exists, it will be replaced with the new article data.
     *
     * @param article The [ArticleEntity] object representing the article to be inserted or updated.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: ArticleEntity)
}
