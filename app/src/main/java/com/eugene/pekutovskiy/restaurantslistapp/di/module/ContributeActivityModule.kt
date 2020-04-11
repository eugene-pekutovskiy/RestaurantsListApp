package com.eugene.pekutovskiy.restaurantslistapp.di.module

import com.eugene.pekutovskiy.restaurantslistapp.ui.MainActivity
import com.eugene.pekutovskiy.restaurantslistapp.ui.restaurantsList.RestaurantsListModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ContributeActivityModule {

    @ContributesAndroidInjector(modules = [RestaurantsListModule::class])
    abstract fun contributesMainActivity(): MainActivity

}