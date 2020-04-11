package com.eugene.pekutovskiy.restaurantslistapp.core.datasource

import com.eugene.pekutovskiy.restaurantslistapp.core.model.Restaurant

interface DataSource {

     fun getRestaurantsData(): List<Restaurant>
}