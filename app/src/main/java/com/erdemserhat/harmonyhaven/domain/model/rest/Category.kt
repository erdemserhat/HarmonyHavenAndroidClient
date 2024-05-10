package com.erdemserhat.harmonyhaven.domain.model.rest

import com.erdemserhat.harmonyhaven.data.local.entities.CategoryEntity
import kotlinx.serialization.Serializable


@Serializable
data class Category(
    val id:Int,
    val name:String,
    val imagePath:String

)

fun Category.toCategoryEntity():CategoryEntity{
    return CategoryEntity(id,name,imagePath)
}