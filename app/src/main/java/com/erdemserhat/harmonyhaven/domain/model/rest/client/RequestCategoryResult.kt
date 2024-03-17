package com.erdemserhat.harmonyhaven.domain.model.rest.client

import com.erdemserhat.harmonyhaven.domain.model.rest.Category
import com.erdemserhat.harmonyhaven.domain.usecase.article.Categories

data class RequestCategoryResult(
    val isReady:Boolean = false,
    val categories:List<Category> = listOf()
)
