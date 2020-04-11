package com.eugene.pekutovskiy.restaurantslistapp.di.module

import android.content.Context
import com.eugene.pekutovskiy.restaurantslistapp.RestaurantsListApplication
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideContext(application: RestaurantsListApplication): Context {
        return application.applicationContext
    }
}