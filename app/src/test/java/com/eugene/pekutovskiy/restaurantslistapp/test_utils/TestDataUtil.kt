package com.eugene.pekutovskiy.restaurantslistapp.test_utils

import android.graphics.Color
import com.eugene.pekutovskiy.restaurantslistapp.core.model.OpeningsState
import com.eugene.pekutovskiy.restaurantslistapp.core.model.Restaurant
import com.eugene.pekutovskiy.restaurantslistapp.core.model.SortingValues
import com.eugene.pekutovskiy.restaurantslistapp.ui.restaurantsList.uimodel.RestaurantUiModel

object TestDataUtil {
    val tanoshiiSushi = Restaurant(
        name = "Tanoshii Sushi",
        openingsState = OpeningsState.OPEN,
        sortingValues = SortingValues(
            bestMatch = 0.0,
            newest = 96.0,
            ratingAverage = 4.5,
            distance = 1190,
            popularity = 17.0,
            averageProductPrice = 1536,
            deliveryCosts = 200,
            minCost = 1000
        )
    )
    val tandooriExpress = Restaurant(
        name = "Tandoori Express",
        openingsState = OpeningsState.CLOSED,
        sortingValues = SortingValues(
            bestMatch = 1.0,
            newest = 266.0,
            ratingAverage = 4.5,
            distance = 2308,
            popularity = 123.0,
            averageProductPrice = 1146,
            deliveryCosts = 150,
            minCost = 1300
        )
    )
    val royalThai = Restaurant(
        name = "Royal Thai",
        openingsState = OpeningsState.ORDER_AHEAD,
        sortingValues = SortingValues(
            bestMatch = 2.0,
            newest = 133.0,
            ratingAverage = 4.5,
            distance = 2639,
            popularity = 44.0,
            averageProductPrice = 1492,
            deliveryCosts = 150,
            minCost = 2500
        )
    )
    val sushiOne = Restaurant(
        name = "Sushi One",
        openingsState = OpeningsState.OPEN,
        sortingValues = SortingValues(
            bestMatch = 3.0,
            newest = 238.0,
            ratingAverage = 4.0,
            distance = 1618,
            popularity = 23.0,
            averageProductPrice = 1285,
            deliveryCosts = 0,
            minCost = 1200
        )
    )
    val rotiShop = Restaurant(
        name = "Roti Shop",
        openingsState = OpeningsState.OPEN,
        sortingValues = SortingValues(
            bestMatch = 4.0,
            newest = 247.0,
            ratingAverage = 4.5,
            distance = 2308,
            popularity = 81.0,
            averageProductPrice = 915,
            deliveryCosts = 0,
            minCost = 2000
        )
    )
    val dailySushi = Restaurant(
        name = "Daily Sushi",
        openingsState = OpeningsState.CLOSED,
        sortingValues = SortingValues(
            bestMatch = 9.0,
            newest = 221.0,
            ratingAverage = 4.0,
            distance = 1911,
            popularity = 6.0,
            averageProductPrice = 1327,
            deliveryCosts = 200,
            minCost = 1000
        )
    )
    val deAmsterdamscheTram = Restaurant(
        name = "De Amsterdamsche Tram",
        openingsState = OpeningsState.OPEN,
        sortingValues = SortingValues(
            bestMatch = 304.0,
            newest = 131.0,
            ratingAverage = 0.0,
            distance = 2792,
            popularity = 0.0,
            averageProductPrice = 892,
            deliveryCosts = 0,
            minCost = 0
        )
    )
    val laleRestaurant = Restaurant(
        name = "Lale Restaurant & Snackbar",
        openingsState = OpeningsState.ORDER_AHEAD,
        sortingValues = SortingValues(
            bestMatch = 305.0,
            newest = 73.0,
            ratingAverage = 0.0,
            distance = 2880,
            popularity = 0.0,
            averageProductPrice = 838,
            deliveryCosts = 0,
            minCost = 0
        )
    )

    val restaurantsData: List<Restaurant> = listOf(
        tanoshiiSushi,
        tandooriExpress,
        royalThai,
        sushiOne,
        rotiShop,
        dailySushi,
        deAmsterdamscheTram,
        laleRestaurant
    )

    val laleRestaurantUiModel = RestaurantUiModel(
        name = "Lale Restaurant & Snackbar",
        openingsStateString = "Open",
        openingsStateTextColor = Color.BLACK,
        rating = "0.0",
        deliveryCosts = "0",
        isFavourite = false,
        topLabelText = "",
        topLabelBackgroundColor = Color.TRANSPARENT
    )
}