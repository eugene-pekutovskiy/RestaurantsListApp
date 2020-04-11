package com.eugene.pekutovskiy.restaurantslistapp.domain.usecase

import com.eugene.pekutovskiy.restaurantslistapp.BuildConfig
import com.eugene.pekutovskiy.restaurantslistapp.core.datasource.local.db.entity.FavouriteRestaurant
import com.eugene.pekutovskiy.restaurantslistapp.core.datasource.repo.RestaurantsListRepository
import com.eugene.pekutovskiy.restaurantslistapp.core.model.Restaurant
import com.eugene.pekutovskiy.restaurantslistapp.core.util.DispatchersFacade
import com.eugene.pekutovskiy.restaurantslistapp.core.util.executeTask
import com.eugene.pekutovskiy.restaurantslistapp.domain.Result
import com.eugene.pekutovskiy.restaurantslistapp.domain.sort.SortOption
import com.eugene.pekutovskiy.restaurantslistapp.domain.sort.SortOptionHandler
import com.eugene.pekutovskiy.restaurantslistapp.domain.util.printSortingResult
import com.eugene.pekutovskiy.restaurantslistapp.ui.restaurantsList.mapper.RestaurantUiModelMapper
import com.eugene.pekutovskiy.restaurantslistapp.ui.restaurantsList.uimodel.RestaurantUiModel
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetRestaurantsDataUseCase @Inject constructor(
    private val repository: RestaurantsListRepository,
    private val sortOptionHandler: SortOptionHandler,
    private val uiModelMapper: RestaurantUiModelMapper,
    private val dispatchersFacade: DispatchersFacade
) : UseCase<SortOption, List<RestaurantUiModel>> {

    override suspend fun execute(arg: SortOption): Result<List<RestaurantUiModel>> {
        return withContext(dispatchersFacade.computation()) {
            val restaurantsDataDeferred = async { repository.getRestaurantsData() }
            val favouritesDataDeferred = async { repository.getFavourites() }
            val restaurantsResult = restaurantsDataDeferred.await()

            // we use hashtable to access items with 0(1) time
            val hashSet: HashSet<String> =
                when (val favouritesResult = favouritesDataDeferred.await()) {
                    is Result.Success -> favouritesResult.data.map(FavouriteRestaurant::title).toHashSet()
                    is Result.Error -> return@withContext Result.Error(favouritesResult.error)
                }

            if (restaurantsResult is Result.Success) {
                val sortedList = sortOptionHandler.sort(restaurantsResult.data, hashSet, arg)
                if (BuildConfig.DEBUG) {
                    printLog(sortedList, arg)
                }
                Result.Success(sortedList.map {
                    uiModelMapper.map(input = it, favourites = hashSet)
                })
            } else {
                val error = (restaurantsResult as Result.Error).error
                Result.Error(error)
            }
        }
    }

    private suspend fun printLog(sortedList: List<Restaurant>, sortOption: SortOption) {
        dispatchersFacade.main().executeTask {
            printSortingResult(sortedList, sortOption)
        }
    }
}