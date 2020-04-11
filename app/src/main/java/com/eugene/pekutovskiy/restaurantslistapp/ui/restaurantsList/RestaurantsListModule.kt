package com.eugene.pekutovskiy.restaurantslistapp.ui.restaurantsList

import androidx.lifecycle.ViewModel
import com.eugene.pekutovskiy.restaurantslistapp.di.factory.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class RestaurantsListModule {

    @ContributesAndroidInjector
    abstract fun contributesRestaurantsListFragment(): RestaurantsListFragment

    @Binds
    @IntoMap
    @ViewModelKey(RestaurantsListViewModel::class)
    abstract fun bindsRestaurantsListViewModel(
        viewModel: RestaurantsListViewModel
    ): ViewModel
}