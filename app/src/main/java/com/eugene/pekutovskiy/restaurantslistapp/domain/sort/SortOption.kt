package com.eugene.pekutovskiy.restaurantslistapp.domain.sort

enum class SortOption {
    DEFAULT, //  represents option when favourite items are on top of a list and the list is sorted by openings state
    BEST_MATCH,
    NEWEST,
    RATING_AVERAGE,
    DISTANCE,
    POPULARITY,
    AVERAGE_PRODUCT_PRICE,
    DELIVERY_COSTS,
    MINIMUM_COST
}
