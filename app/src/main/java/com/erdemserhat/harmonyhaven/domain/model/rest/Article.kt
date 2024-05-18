package com.erdemserhat.harmonyhaven.domain.model.rest

import android.os.Parcel
import android.os.Parcelable
import com.erdemserhat.harmonyhaven.data.local.entities.ArticleEntity
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Serializable
data class Article(
    val id:Int=0,
    val title:String="",
    val content:String="",
    val contentPreview:String="",
    val publishDate:String="",
    val categoryId:Int=0,
    val imagePath:String=""

)

@Serializable
@Parcelize
data class ArticleResponseType(
    val id:Int=0,
    val title:String="",
    val content:String="",
    val contentPreview:String ="",
    val publishDate:String="",
    val category:String?="",
    val imagePath:String=""

) : Parcelable


fun Article.toArticleResponseType(categoryList:List<Category>):ArticleResponseType{
    return ArticleResponseType(
        id = id,
        title = title,
        content = content,
        contentPreview = contentPreview,
        publishDate = publishDate,
        category = categoryList.find { it.id==categoryId }?.name ?:"Null Category ID",
        imagePath = imagePath
    )
}


fun Article.toArticleEntity():ArticleEntity{
    return ArticleEntity(
        id, title, content,contentPreview, publishDate, categoryId, imagePath
    )
}


