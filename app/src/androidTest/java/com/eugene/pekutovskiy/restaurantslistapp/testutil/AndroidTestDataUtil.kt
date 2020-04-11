package com.eugene.pekutovskiy.restaurantslistapp.testutil

import android.graphics.Color
import com.eugene.pekutovskiy.restaurantslistapp.ui.restaurantsList.uimodel.RestaurantUiModel

object AndroidTestDataUtil {


    val laleRestaurantUiModel = RestaurantUiModel(
        name = "Lale Restaurant & Snackbar",
        openingsStateString = "order ahead",
        openingsStateTextColor = Color.BLACK,
        rating = "0.0",
        deliveryCosts = "0",
        isFavourite = false,
        topLabelText = "",
        topLabelBackgroundColor = Color.TRANSPARENT
    )

    val tanoshiSushi = RestaurantUiModel(
        name = "Tanoshii Sushi",
        openingsStateString = "Open",
        openingsStateTextColor = Color.BLACK,
        rating = "4.5",
        deliveryCosts = "200",
        isFavourite = false,
        topLabelText = "",
        topLabelBackgroundColor = Color.TRANSPARENT
    )

    val dailySushi = RestaurantUiModel(
        name = "Daily Sushi",
        openingsStateString = "closed",
        openingsStateTextColor = Color.RED,
        rating = "4.0",
        deliveryCosts = "200",
        isFavourite = false,
        topLabelText = "",
        topLabelBackgroundColor = Color.TRANSPARENT
    )

    val dmsterdamscheTram = RestaurantUiModel(
        name = "De Amsterdamsche Tram",
        openingsStateString = "Open",
        openingsStateTextColor = Color.BLACK,
        rating = "0.0",
        deliveryCosts = "0",
        isFavourite = false,
        topLabelText = "",
        topLabelBackgroundColor = Color.TRANSPARENT
    )

    val tandooriExpress = RestaurantUiModel(
        name = "Tandoori Express",
        openingsStateString = "closed",
        openingsStateTextColor = Color.RED,
        rating = "4.5",
        deliveryCosts = "150",
        isFavourite = false,
        topLabelText = "",
        topLabelBackgroundColor = Color.TRANSPARENT
    )


    val restaurantsList = listOf(
        tanoshiSushi,
        dmsterdamscheTram,
        laleRestaurantUiModel,
        tandooriExpress,
        dailySushi
    )
}