package com.eugene.pekutovskiy.restaurantslistapp.domain.util

import android.annotation.SuppressLint
import com.eugene.pekutovskiy.restaurantslistapp.core.model.Restaurant
import com.eugene.pekutovskiy.restaurantslistapp.domain.sort.SortOption
import timber.log.Timber

@SuppressLint("DefaultLocale")
fun printSortingResult(sortedList: List<Restaurant>, sortOption: SortOption) {
    val sb = StringBuilder()
    sortedList.forEach {
        sb.append(it.name)
            .append(", ")
            .append("openings state : ${it.openingsState.toString().toUpperCase()}, ")
            .append(
                when (sortOption) {
                    SortOption.DEFAULT -> ""
                    SortOption.BEST_MATCH -> it.sortingValues.bestMatch
                    SortOption.NEWEST -> it.sortingValues.newest
                    SortOption.RATING_AVERAGE -> it.sortingValues.ratingAverage
                    SortOption.DISTANCE -> it.sortingValues.distance
                    SortOption.POPULARITY -> it.sortingValues.popularity
                    SortOption.AVERAGE_PRODUCT_PRICE -> it.sortingValues.averageProductPrice
                    SortOption.DELIVERY_COSTS -> it.sortingValues.deliveryCosts
                    SortOption.MINIMUM_COST -> it.sortingValues.minCost
                }
            )
            .append("\n")
    }
    Timber.tag("SORT_OPTION_DEBUG").d("--------------------------------------------")
    Timber.tag("SORT_OPTION_DEBUG").d("sort option = $sortOption  sortedList = \n$sb")
}