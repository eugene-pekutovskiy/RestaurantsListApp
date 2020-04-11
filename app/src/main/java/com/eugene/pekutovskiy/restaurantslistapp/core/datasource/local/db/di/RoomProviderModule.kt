package com.eugene.pekutovskiy.restaurantslistapp.core.datasource.local.db.di

import android.content.Context
import androidx.room.Room
import com.eugene.pekutovskiy.restaurantslistapp.core.datasource.local.db.FavouritesDatabase
import com.eugene.pekutovskiy.restaurantslistapp.core.datasource.local.db.constants.LocalStoreConstants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomProviderModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(context: Context): FavouritesDatabase {
        return Room.databaseBuilder(
            context,
            FavouritesDatabase::class.java,
            LocalStoreConstants.DATABASE_NAME
        ).build()
    }
}