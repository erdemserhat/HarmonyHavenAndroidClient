package com.erdemserhat.harmonyhaven.domain.model.rest

import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val id:Int,
    val title:String,
    val content:String,
    val publishDate:String,
    val categoryId:Int,
    val imagePath:String

)

@Serializable
data class ArticleResponseType(
    val id:Int,
    val title:String,
    val content:String,
    val publishDate:String,
    val category:String?,
    val imagePath:String

)

fun Article.toArticleResponseType(categoryList:List<Category>):ArticleResponseType{
    return ArticleResponseType(
        id = id,
        title = title,
        content = content,
        publishDate = publishDate,
        category = categoryList.find { it.id==categoryId }?.name ?:"Null Category ID",
        imagePath = imagePath
    )
}

