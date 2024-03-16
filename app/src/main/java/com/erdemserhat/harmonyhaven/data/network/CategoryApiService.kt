package com.erdemserhat.harmonyhaven.data.network

import com.erdemserhat.harmonyhaven.domain.model.rest.Category
import retrofit2.http.GET

interface CategoryApiService {
    @GET("categories")
    suspend fun getAllCategories():List<Category>

}