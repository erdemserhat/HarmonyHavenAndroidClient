package com.erdemserhat.harmonyhaven.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.erdemserhat.harmonyhaven.data.local.entities.ArticleEntity


@Dao
interface ArticleDao {

    @Query("SELECT * FROM articles")
    suspend fun getArticles(): List<ArticleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: ArticleEntity)
}
