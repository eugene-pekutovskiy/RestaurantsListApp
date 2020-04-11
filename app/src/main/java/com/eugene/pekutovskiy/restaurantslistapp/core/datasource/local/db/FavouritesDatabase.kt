package com.eugene.pekutovskiy.restaurantslistapp.core.datasource.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eugene.pekutovskiy.restaurantslistapp.core.datasource.local.db.entity.FavouriteRestaurant

@Database(entities = [FavouriteRestaurant::class], version = 1, exportSchema = false)
abstract class FavouritesDatabase : RoomDatabase() {

    abstract fun getFavouritesDao(): FavouritesDao
}