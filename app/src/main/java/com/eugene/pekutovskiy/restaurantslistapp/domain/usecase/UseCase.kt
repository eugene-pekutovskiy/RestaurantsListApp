package com.eugene.pekutovskiy.restaurantslistapp.domain.usecase

import com.eugene.pekutovskiy.restaurantslistapp.domain.Result

interface UseCase<T, R> {
    suspend fun execute(arg: T): Result<R>
}