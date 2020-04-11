package com.eugene.pekutovskiy.restaurantslistapp.domain.sort

import com.eugene.pekutovskiy.restaurantslistapp.core.model.Restaurant
import javax.inject.Inject

class SortOptionHandler @Inject constructor() {

    fun sort(
        restaurantsData: List<Restaurant>,
        favourites: HashSet<String>,
        sortOption: SortOption
    ): List<Restaurant> {

        fun isFavourite(name: String): Boolean {
            return favourites.contains(name)
        }

        //create favourites comparator only if favourites list is not empty
        val favouriteRestaurantsComparator = if (favourites.isEmpty()) {
            null
        } else Comparator { r1: Restaurant, r2: Restaurant ->
            when {
                isFavourite(r1.name) && !isFavourite(r2.name) -> -1
                !isFavourite(r1.name) && isFavourite(r2.name) -> 1
                else -> 0
            }
        }

        return handleInternally(restaurantsData, favouriteRestaurantsComparator, sortOption)
    }


    private fun handleInternally(
        restaurantsData: List<Restaurant>,
        favouriteRestaurantsComparator: Comparator<Restaurant>?,
        sortOption: SortOption
    ): List<Restaurant> {

        val defaultComparator = favouriteRestaurantsComparator?.thenBy {
            it.openingsState.ordinal
        } ?: compareBy {
            it.openingsState.ordinal
        }

        // apply SortOption to default comparator
        val compositeComparator = when (sortOption) {
            SortOption.DEFAULT -> {
                defaultComparator
            }
            SortOption.BEST_MATCH -> {
                defaultComparator.thenByDescending {
                    it.sortingValues.bestMatch
                }
            }
            SortOption.NEWEST -> {
                defaultComparator.thenByDescending {
                    it.sortingValues.newest
                }
            }
            SortOption.RATING_AVERAGE -> {
                defaultComparator.thenByDescending {
                    it.sortingValues.ratingAverage
                }
            }
            SortOption.DISTANCE -> {
                defaultComparator.thenBy {
                    it.sortingValues.distance
                }
            }
            SortOption.POPULARITY -> {
                defaultComparator.thenByDescending {
                    it.sortingValues.popularity
                }
            }
            SortOption.AVERAGE_PRODUCT_PRICE -> {
                defaultComparator.thenBy {
                    it.sortingValues.averageProductPrice
                }
            }
            SortOption.DELIVERY_COSTS -> {
                defaultComparator.thenBy {
                    it.sortingValues.deliveryCosts
                }
            }
            SortOption.MINIMUM_COST -> {
                defaultComparator.thenBy {
                    it.sortingValues.minCost
                }
            }
        }
        return restaurantsData.sortedWith(compositeComparator)
    }
}

