package com.erdemserhat.harmonyhaven.domain.usecase.article

import com.erdemserhat.harmonyhaven.domain.model.rest.Category
import javax.inject.Inject

class Categories @Inject constructor(

) {
    suspend operator fun invoke(): List<Category> {
        try {
            //return categoryApiService.getAllCategories()
            return listOf(Category(1,"",""))

        } catch (_: Exception) {
            throw Exception("Category Network Error")
        }


    }

}