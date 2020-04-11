package com.eugene.pekutovskiy.restaurantslistapp.core.datasource.repo

import com.eugene.pekutovskiy.restaurantslistapp.domain.Result
import com.eugene.pekutovskiy.restaurantslistapp.core.datasource.DataSource
import com.eugene.pekutovskiy.restaurantslistapp.core.datasource.local.db.FavouritesDatabase
import com.eugene.pekutovskiy.restaurantslistapp.core.datasource.local.db.entity.FavouriteRestaurant
import com.eugene.pekutovskiy.restaurantslistapp.core.model.Restaurant
import com.eugene.pekutovskiy.restaurantslistapp.core.util.DispatchersFacade
import com.eugene.pekutovskiy.restaurantslistapp.core.util.executeTask
import java.util.*
import javax.inject.Inject

class RestaurantsListRepositoryImpl @Inject constructor(
    private val favouritesDb: FavouritesDatabase,
    private val dataSource: DataSource,
    private val dispatchersFacade: DispatchersFacade
) : RestaurantsListRepository {

    private var cache: List<Restaurant>? = null

    override suspend fun getRestaurantsData(): Result<List<Restaurant>> =
        dispatchersFacade.io().executeTask {
            cache?.let {
                return@executeTask it
            }
            dataSource.getRestaurantsData().also {
                cache = Collections.synchronizedList(it)
            }
        }


    override suspend fun getFavourites(): Result<List<FavouriteRestaurant>> =
        dispatchersFacade.io().executeTask {
            favouritesDb.getFavouritesDao().getFavourites()
        }


    override suspend fun addToFavourites(restaurantName: String) =
        dispatchersFacade.io().executeTask {
            favouritesDb.getFavouritesDao().addToFavourites(
                FavouriteRestaurant(restaurantName)
            )
        }

    override suspend fun removeFromFavourites(restaurantName: String) =
        dispatchersFacade.io().executeTask {
            favouritesDb.getFavouritesDao().removeFromFavourites(restaurantName)
        }
}
