package com.eugene.pekutovskiy.restaurantslistapp.core.util

import com.eugene.pekutovskiy.restaurantslistapp.domain.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

suspend fun <T> CoroutineDispatcher.executeTask(task: suspend () -> T): Result<T> {
    return withContext(this) {
        try {
            Result.Success(task())
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }
}