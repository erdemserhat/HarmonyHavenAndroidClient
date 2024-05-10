package com.erdemserhat.harmonyhaven.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.erdemserhat.harmonyhaven.domain.model.rest.Category


@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val imagePath: String
)

fun CategoryEntity.toCategory(): Category {
    return Category(id, name, imagePath)
}