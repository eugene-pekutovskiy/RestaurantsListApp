package com.eugene.pekutovskiy.restaurantslistapp.ui.restaurantsList.mapper

import android.content.Context
import com.eugene.pekutovskiy.restaurantslistapp.R
import com.eugene.pekutovskiy.restaurantslistapp.domain.sort.SortOption
import javax.inject.Inject

class StringToSortOptionMapper @Inject constructor(
    private val context: Context
) {

    fun map(input: String): SortOption = when (input) {
        context.getString(R.string.sort_option_default) -> SortOption.DEFAULT
        context.getString(R.string.sort_option_best_match) -> SortOption.BEST_MATCH
        context.getString(R.string.sort_option_newest) -> SortOption.NEWEST
        context.getString(R.string.sort_option_rating_average) -> SortOption.RATING_AVERAGE
        context.getString(R.string.sort_option_distance) -> SortOption.DISTANCE
        context.getString(R.string.sort_option_popularity) -> SortOption.POPULARITY
        context.getString(R.string.sort_option_average_product_price) -> SortOption.AVERAGE_PRODUCT_PRICE
        context.getString(R.string.sort_option_delivery_costs) -> SortOption.DELIVERY_COSTS
        context.getString(R.string.sort_option_minimum_cost) -> SortOption.MINIMUM_COST
        else -> throw IllegalArgumentException("Unknown sort option")
    }
}