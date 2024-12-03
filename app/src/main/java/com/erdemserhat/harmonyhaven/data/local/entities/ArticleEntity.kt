package com.erdemserhat.harmonyhaven.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.erdemserhat.harmonyhaven.domain.model.rest.Article

/**
 * Represents an article entity in the 'articles' table of the database.
 *
 * This data class is used to define the structure of an article record stored in the database.
 * Each property maps to a column in the 'articles' table.
 *
 * @property id The unique identifier of the article. It serves as the primary key for the table.
 * @property title The title of the article.
 * @property content The full content of the article.
 * @property contentPreview A brief preview or excerpt from the article content.
 * @property publishDate The date when the article was published, typically in a string format.
 * @property categoryId The ID of the category to which the article belongs.
 * @property imagePath The file path or URL of the image associated with the article.
 */
@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val content: String,
    val contentPreview: String,
    val publishDate: String,
    val categoryId: Int,
    val imagePath: String
)


/**
 * Extension function to convert an [ArticleEntity] to an [Article].
 *
 * This function transforms the database entity representation into a domain model object.
 *
 * @return An [Article] object with properties mapped from the [ArticleEntity].
 */
fun ArticleEntity.toArticle(): Article {
    return Article(
        id = id,
        title = title,
        content = content,
        contentPreview = contentPreview,
        publishDate = publishDate,
        categoryId = categoryId,
        imagePath = imagePath
    )
}
