package com.erdemserhat.harmonyhaven.data.local.repository

import com.erdemserhat.harmonyhaven.data.local.dao.CategoryDao
import com.erdemserhat.harmonyhaven.data.local.entities.CategoryEntity
import javax.inject.Inject

/**
 * Repository class for managing category data.
 *
 * This class provides methods to interact with the [CategoryDao] for performing database operations related to categories.
 * It acts as an intermediary between the data layer (DAO) and the rest of the application.
 *
 * @constructor Creates an instance of [CategoryRepository] with the given [CategoryDao] dependency.
 * @property categoryDao The DAO (Data Access Object) used to perform operations on the 'categories' table.
 */
class CategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao
) {
    /**
     * Retrieves a list of all categories from the database.
     *
     * This method fetches all category records stored in the 'categories' table.
     *
     * @return A list of [CategoryEntity] objects representing the categories.
     */
    suspend fun getCategories(): List<CategoryEntity> {
        return categoryDao.getCategories()
    }

    /**
     * Saves a new category to the database.
     *
     * This method inserts or updates the given category record in the 'categories' table.
     * If the category already exists (based on its primary key), it will be replaced.
     *
     * @param category The [CategoryEntity] object to be inserted or updated in the database.
     */
    suspend fun saveCategory(category: CategoryEntity) {
        categoryDao.insertCategory(category)
    }
}
