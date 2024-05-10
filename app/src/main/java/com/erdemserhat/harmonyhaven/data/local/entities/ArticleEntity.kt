package com.erdemserhat.harmonyhaven.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.erdemserhat.harmonyhaven.domain.model.rest.Article


@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val content: String,
    val publishDate: String,
    val categoryId:Int,
    val imagePath:String,

)

fun ArticleEntity.toArticle():Article{
    return Article(
        id, title, content,publishDate,categoryId,imagePath
    )
}