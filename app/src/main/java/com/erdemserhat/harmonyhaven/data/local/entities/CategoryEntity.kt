package com.erdemserhat.harmonyhaven.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.erdemserhat.harmonyhaven.domain.model.rest.Category


/**
 * Represents a category entity in the 'categories' table of the database.
 *
 * This data class defines the structure of a category record stored in the database.
 * Each property maps to a column in the 'categories' table.
 *
 * @property id The unique identifier of the category. It serves as the primary key for the table.
 * @property name The name of the category.
 * @property imagePath The file path or URL of the image associated with the category.
 */
@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val imagePath: String
)

/**
 * Extension function to convert a [CategoryEntity] to a [Category].
 *
 * This function transforms the database entity representation into a domain model object.
 *
 * @return A [Category] object with properties mapped from the [CategoryEntity].
 */
fun CategoryEntity.toCategory(): Category {
    return Category(
        id = id,
        name = name,
        imagePath = imagePath
    )
}
