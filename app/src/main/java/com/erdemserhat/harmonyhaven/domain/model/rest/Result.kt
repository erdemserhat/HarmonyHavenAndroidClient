package com.erdemserhat.harmonyhaven.domain.model.rest

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
} 