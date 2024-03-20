package com.erdemserhat.harmonyhaven.domain.usecase.article

import com.erdemserhat.harmonyhaven.data.network.CategoryApiService
import com.erdemserhat.harmonyhaven.domain.model.rest.client.RequestCategoryResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Categories @Inject constructor(
    private val categoryApiService: CategoryApiService
) {
    suspend operator fun invoke(): Flow<RequestCategoryResult> = flow{

        emit(RequestCategoryResult())

        val categorySchema = categoryApiService.getAllCategories()

        emit(RequestCategoryResult(true,categorySchema))


    }

}