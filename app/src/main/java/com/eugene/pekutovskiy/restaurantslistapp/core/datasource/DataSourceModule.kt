package com.eugene.pekutovskiy.restaurantslistapp.core.datasource

import com.eugene.pekutovskiy.restaurantslistapp.core.datasource.local.RawResourceDataSource
import com.eugene.pekutovskiy.restaurantslistapp.core.datasource.repo.RestaurantsListRepository
import com.eugene.pekutovskiy.restaurantslistapp.core.datasource.repo.RestaurantsListRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class DataSourceModule {

    @Binds
    abstract fun bindsRawResourcesDataSource(
        dataSource: RawResourceDataSource
    ): DataSource

    @Binds
    abstract fun bindsRestaurantsListRepo(
        repository: RestaurantsListRepositoryImpl
    ) : RestaurantsListRepository
}