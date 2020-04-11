package com.eugene.pekutovskiy.restaurantslistapp.ui.restaurantsList

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eugene.pekutovskiy.restaurantslistapp.annotations.OpenForTesting
import com.eugene.pekutovskiy.restaurantslistapp.domain.Result
import com.eugene.pekutovskiy.restaurantslistapp.domain.usecase.AddToFavouritesUseCase
import com.eugene.pekutovskiy.restaurantslistapp.domain.usecase.GetRestaurantsDataUseCase
import com.eugene.pekutovskiy.restaurantslistapp.domain.usecase.RemoveFromFavouritesUseCase
import com.eugene.pekutovskiy.restaurantslistapp.ui.restaurantsList.mapper.StringToSortOptionMapper
import com.eugene.pekutovskiy.restaurantslistapp.ui.restaurantsList.uimodel.RestaurantUiModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@OpenForTesting
class RestaurantsListViewModel @Inject constructor(
    private val getRestaurantsDataUseCase: GetRestaurantsDataUseCase,
    private val addToFavouritesUseCase: AddToFavouritesUseCase,
    private val removeFromFavouritesUseCase: RemoveFromFavouritesUseCase,
    private val stringToSortOptionMapper: StringToSortOptionMapper
) : ViewModel() {

    val uiEventLiveData = MutableLiveData<UiEvent>()
    @VisibleForTesting
    internal var filteringByNameQuery: String? = null

    fun getRestaurantsData(stringItem: String) {
        viewModelScope.launch {
            val sortOption = stringToSortOptionMapper.map(stringItem)
            uiEventLiveData.value =
                when (val result = getRestaurantsDataUseCase.execute(sortOption)) {
                    is Result.Success -> UiEvent.UpdateRestaurantsList(result.data)
                    is Result.Error -> UiEvent.Error(result.error)
                }
        }
    }

    fun addToFavourites(restaurantName: String) {
        viewModelScope.launch {
            uiEventLiveData.value =
                when (val result = addToFavouritesUseCase.execute(restaurantName)) {
                    is Result.Success -> UiEvent.AddedToFavourites
                    is Result.Error -> UiEvent.Error(result.error)
                }
        }
    }

    fun removeFromFavourites(restaurantName: String) {
        viewModelScope.launch {
            uiEventLiveData.value =
                when (val result = removeFromFavouritesUseCase.execute(restaurantName)) {
                    is Result.Success -> UiEvent.RemovedFromFavourites
                    is Result.Error -> UiEvent.Error(result.error)
                }
        }
    }

    fun filterByRestaurantName(query: String, stringSortOption: String) {
        if (query.isBlank()) {
            return
        }
        val previousQueryStr = filteringByNameQuery
        if (previousQueryStr != null && query == previousQueryStr) {
            return
        }

        viewModelScope.launch {
            Timber.d("Filtering restaurants by query $query")
            val sortOption = stringToSortOptionMapper.map(stringSortOption)
            val result = getRestaurantsDataUseCase.execute(sortOption)
            if (result is Result.Error) {
                uiEventLiveData.value = UiEvent.Error(result.error)
                return@launch
            }

            val resultData = (result as Result.Success).data
            val filteredList = resultData.filter {
                it.name.contains(query, ignoreCase = true)
            }
            uiEventLiveData.value = if (filteredList.isNotEmpty()) {
                UiEvent.UpdateRestaurantsList(filteredList)
            } else {
                UiEvent.NoMatchesFound
            }
            filteringByNameQuery = query
        }
    }

    sealed class UiEvent {
        class UpdateRestaurantsList(val items: List<RestaurantUiModel>) : UiEvent()
        class Error(val error: Throwable) : UiEvent()
        object ShowLoading : UiEvent()
        object AddedToFavourites : UiEvent()
        object RemovedFromFavourites : UiEvent()
        object NoMatchesFound : UiEvent()
    }
}