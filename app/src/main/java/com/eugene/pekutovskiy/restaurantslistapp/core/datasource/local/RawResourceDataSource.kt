package com.eugene.pekutovskiy.restaurantslistapp.core.datasource.local

import com.eugene.pekutovskiy.restaurantslistapp.R
import com.eugene.pekutovskiy.restaurantslistapp.core.datasource.DataSource
import com.eugene.pekutovskiy.restaurantslistapp.core.model.Restaurant
import com.eugene.pekutovskiy.restaurantslistapp.core.model.RestaurantsResponse
import com.eugene.pekutovskiy.restaurantslistapp.core.util.ParseJsonHelper
import javax.inject.Inject

class RawResourceDataSource @Inject constructor(
    private val parseJsonHelper: ParseJsonHelper
) : DataSource {

    override fun getRestaurantsData(): List<Restaurant> =
        parseJsonHelper.constructWithGson(
            R.raw.restaurantsdata,
            RestaurantsResponse::class.java
        ).restaurants
}
