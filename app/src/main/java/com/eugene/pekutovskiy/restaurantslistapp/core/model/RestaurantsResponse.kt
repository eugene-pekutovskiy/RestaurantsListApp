package com.eugene.pekutovskiy.restaurantslistapp.core.model

import com.google.gson.annotations.SerializedName

data class RestaurantsResponse(
    @SerializedName("restaurants")
    val restaurants: List<Restaurant>
)