package com.erdemserhat.harmonyhaven.domain.model.rest

import android.os.Parcelable
import com.erdemserhat.harmonyhaven.data.local.entities.ArticleEntity
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Serializable
data class Article(
    var id:Int=0,
    val title:String="",
    val slug:String="",
    val content:String="",
    val contentPreview:String="",
    val publishDate:String="",
    val categoryId:Int=0,
    val imagePath:String=""

)

@Serializable
@Parcelize
data class ArticlePresentableUIModel(
    val id:Int=0,
    val title:String="",
    val ready:Boolean = false,
    val content:String="",
    val slug: String="",
    val contentPreview:String ="",
    val publishDate:String="",
    val category:String?="",
    val imagePath:String=""

) : Parcelable


fun Article.toArticleResponseType(categoryList:List<Category>,ready: Boolean = false):ArticlePresentableUIModel{
    return ArticlePresentableUIModel(
        id = id,
        title = title,
        slug = slug,
        ready = ready,
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


