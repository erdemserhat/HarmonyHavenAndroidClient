package com.erdemserhat.harmonyhaven.data.local.repository

import com.erdemserhat.harmonyhaven.data.local.dao.CategoryDao
import com.erdemserhat.harmonyhaven.data.local.entities.ArticleEntity
import com.erdemserhat.harmonyhaven.data.local.entities.CategoryEntity
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao
){
    suspend fun getCategories(): List<CategoryEntity> {
        return categoryDao.getCategories()

    }

    suspend fun saveCategory(category: CategoryEntity) {
        categoryDao.insertCategory(category)
    }
}
