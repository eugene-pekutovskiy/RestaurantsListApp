package com.eugene.pekutovskiy.restaurantslistapp.ui.restaurantsList.mapper

import android.content.Context
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.eugene.pekutovskiy.restaurantslistapp.R
import com.eugene.pekutovskiy.restaurantslistapp.core.model.OpeningsState
import com.eugene.pekutovskiy.restaurantslistapp.core.model.Restaurant
import com.eugene.pekutovskiy.restaurantslistapp.ui.restaurantsList.uimodel.RestaurantUiModel
import javax.inject.Inject

class RestaurantUiModelMapper @Inject constructor(
    private val context: Context
) {

    fun map(input: Restaurant, favourites: HashSet<String>): RestaurantUiModel {
        val displayFavouriteLabel = favourites.contains(input.name)
        // dummy logic. display green "new" label if sortingValues.newest >= 200
        val displayNewLabel = !displayFavouriteLabel && input.sortingValues.newest >= 200

        return with(input) {
            RestaurantUiModel(
                name = name,
                openingsStateString = openingsStateToString(openingsState),
                openingsStateTextColor = getOpeningsStateTextColor(openingsState),
                rating = sortingValues.ratingAverage.toString(),
                deliveryCosts = getDeliveryCostTxt(sortingValues.deliveryCosts),
                isFavourite = displayFavouriteLabel,
                topLabelText = context.getString(
                    when {
                        displayFavouriteLabel -> R.string.favourite_label
                        displayNewLabel -> R.string.new_label
                        else -> R.string.empty
                    }
                ),
                topLabelBackgroundColor = ContextCompat.getColor(
                    context,
                    when {
                        displayFavouriteLabel -> R.color.orange
                        displayNewLabel -> R.color.green
                        else -> android.R.color.transparent
                    }
                )
            )
        }
    }

    private fun openingsStateToString(openingsState: OpeningsState) =
        context.getString(
            when (openingsState) {
                OpeningsState.CLOSED -> R.string.openings_state_closed
                OpeningsState.OPEN -> R.string.openings_state_open
                OpeningsState.ORDER_AHEAD -> R.string.openings_state_order_ahead
            }
        )

    @ColorInt
    private fun getOpeningsStateTextColor(openingsState: OpeningsState): Int {
        return ContextCompat.getColor(
            context,
            if (openingsState == OpeningsState.CLOSED) {
                R.color.colorPrimary
            } else {
                R.color.green
            }
        )
    }

    private fun getDeliveryCostTxt(deliveryCost: Int) =
        if (deliveryCost == 0) {
            context.getString(R.string.delivery_cost_free)
        } else {
            "$deliveryCost $"
        }

}