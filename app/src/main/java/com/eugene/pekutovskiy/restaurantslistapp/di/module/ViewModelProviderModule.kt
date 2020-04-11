package com.eugene.pekutovskiy.restaurantslistapp.di.module

import androidx.lifecycle.ViewModelProvider
import com.eugene.pekutovskiy.restaurantslistapp.di.factory.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelProviderModule {

    @Binds
    internal abstract fun bindViewModelFactory(
        factory: ViewModelFactory
    ): ViewModelProvider.Factory
}