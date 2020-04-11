package com.eugene.pekutovskiy.restaurantslistapp.domain.usecase

import com.eugene.pekutovskiy.restaurantslistapp.domain.Result
import com.eugene.pekutovskiy.restaurantslistapp.core.datasource.repo.RestaurantsListRepository
import javax.inject.Inject

class AddToFavouritesUseCase @Inject constructor(
    private val repository: RestaurantsListRepository
) : UseCase<String, Unit> {

    override suspend fun execute(arg: String): Result<Unit> =
        repository.addToFavourites(restaurantName = arg)

}