package com.eugene.pekutovskiy.restaurantslistapp.core.datasource.repo

import com.eugene.pekutovskiy.restaurantslistapp.core.datasource.local.db.entity.FavouriteRestaurant
import com.eugene.pekutovskiy.restaurantslistapp.core.model.Restaurant
import com.eugene.pekutovskiy.restaurantslistapp.domain.Result

interface RestaurantsListRepository {

    suspend fun getRestaurantsData(): Result<List<Restaurant>>

    suspend fun getFavourites(): Result<List<FavouriteRestaurant>>

    suspend fun addToFavourites(restaurantName: String): Result<Unit> // Result<Unit> here is kinda Rx Completable

    suspend fun removeFromFavourites(restaurantName: String): Result<Unit> // Result<Unit> here is kinda Rx Completable
}