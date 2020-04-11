package com.eugene.pekutovskiy.restaurantslistapp.ui.restaurantsList.adapter

import androidx.recyclerview.widget.DiffUtil
import com.eugene.pekutovskiy.restaurantslistapp.ui.restaurantsList.uimodel.RestaurantUiModel
import javax.inject.Inject

class RestaurantsListDiffCallback @Inject constructor() :
    DiffUtil.ItemCallback<RestaurantUiModel>() {
    override fun areItemsTheSame(oldItem: RestaurantUiModel, newItem: RestaurantUiModel): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(
        oldItem: RestaurantUiModel,
        newItem: RestaurantUiModel
    ): Boolean {
        return oldItem.isFavourite == newItem.isFavourite
    }

    override fun getChangePayload(oldItem: RestaurantUiModel, newItem: RestaurantUiModel): Any? {
        val updates = mutableSetOf<PayloadUpdates>()
        if (oldItem.isFavourite.compareTo(newItem.isFavourite) != 0) {
            updates.add(PayloadUpdates.FAVOURITE_STATUS)
        }
        return updates
    }
}

enum class PayloadUpdates {
    FAVOURITE_STATUS
}
