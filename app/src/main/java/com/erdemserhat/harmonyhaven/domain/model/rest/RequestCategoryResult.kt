package com.erdemserhat.harmonyhaven.domain.model.rest

data class RequestCategoryResult(
    val isReady:Boolean = false,
    val categories:List<Category> = listOf()
)
