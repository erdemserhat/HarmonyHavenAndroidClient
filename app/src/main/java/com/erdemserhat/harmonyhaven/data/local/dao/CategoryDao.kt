package com.erdemserhat.harmonyhaven.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.erdemserhat.harmonyhaven.data.local.entities.CategoryEntity


@Dao
interface CategoryDao {

    /**
     * Retrieves all categories from the database.
     *
     * This method performs a query to fetch all categories stored in the 'categories' table.
     * It returns a list of [CategoryEntity] objects representing the categories.
     *
     * @return A list of [CategoryEntity] objects containing all categories from the database.
     */
    @Query("SELECT * FROM categories")
    suspend fun getCategories(): List<CategoryEntity>

    /**
     * Inserts a new category into the database or updates an existing category if it already exists.
     *
     * This method performs an insert operation on the 'categories' table. If a category with the same
     * primary key already exists, it will be replaced with the new category data.
     *
     * @param category The [CategoryEntity] object representing the category to be inserted or updated.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity)
}
