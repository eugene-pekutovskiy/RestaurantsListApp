package com.eugene.pekutovskiy.restaurantslistapp.domain

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val error: Throwable) : Result<Nothing>()
}