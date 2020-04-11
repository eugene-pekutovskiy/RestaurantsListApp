package com.eugene.pekutovskiy.restaurantslistapp.core.model

import com.google.gson.annotations.SerializedName

data class Restaurant(
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val openingsState: OpeningsState,
    @SerializedName("sortingValues")
    val sortingValues: SortingValues
)