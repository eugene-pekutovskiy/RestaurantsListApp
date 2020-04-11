package com.eugene.pekutovskiy.restaurantslistapp.core.model

import com.google.gson.annotations.SerializedName


data class SortingValues(
    @SerializedName("bestMatch")
    val bestMatch: Double,
    @SerializedName("newest")
    val newest: Double,
    @SerializedName("ratingAverage")
    val ratingAverage: Double,
    @SerializedName("distance")
    val distance: Int,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("averageProductPrice")
    val averageProductPrice: Int,
    @SerializedName("deliveryCosts")
    val deliveryCosts: Int,
    @SerializedName("minCost")
    val minCost: Int
)