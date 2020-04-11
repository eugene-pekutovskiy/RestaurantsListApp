package com.eugene.pekutovskiy.restaurantslistapp.ui.restaurantsList.uimodel

import androidx.annotation.ColorInt

data class RestaurantUiModel(
    val name: String,
    val openingsStateString: String,
    @ColorInt
    val openingsStateTextColor: Int,
    val rating: String,
    val deliveryCosts: String,
    val isFavourite: Boolean,
    val topLabelText: String,
    @ColorInt
    val topLabelBackgroundColor: Int

)