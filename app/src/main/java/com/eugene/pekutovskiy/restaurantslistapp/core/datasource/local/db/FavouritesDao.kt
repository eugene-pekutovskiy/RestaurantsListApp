package com.eugene.pekutovskiy.restaurantslistapp.core.datasource.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eugene.pekutovskiy.restaurantslistapp.core.datasource.local.db.constants.LocalStoreConstants.TABLE_NAME_FAVOURITES
import com.eugene.pekutovskiy.restaurantslistapp.core.datasource.local.db.entity.FavouriteRestaurant

@Dao
interface FavouritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavourites(restaurant: FavouriteRestaurant)

    @Query("DELETE FROM $TABLE_NAME_FAVOURITES WHERE title = :restaurantName")
    suspend fun removeFromFavourites(restaurantName : String)

    @Query("SELECT * FROM $TABLE_NAME_FAVOURITES")
    suspend fun getFavourites(): List<FavouriteRestaurant>
}