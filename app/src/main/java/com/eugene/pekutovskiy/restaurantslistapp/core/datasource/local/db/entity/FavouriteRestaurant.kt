package com.eugene.pekutovskiy.restaurantslistapp.core.datasource.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eugene.pekutovskiy.restaurantslistapp.core.datasource.local.db.constants.LocalStoreConstants

@Entity(tableName = LocalStoreConstants.TABLE_NAME_FAVOURITES)
data class FavouriteRestaurant(
    val title: String,
    @PrimaryKey
    val id: Int = title.hashCode()
)