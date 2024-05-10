package com.erdemserhat.harmonyhaven.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.erdemserhat.harmonyhaven.data.local.entities.ArticleEntity
import com.erdemserhat.harmonyhaven.data.local.entities.CategoryEntity


@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories")
    suspend fun getCategories():List<CategoryEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity)
}