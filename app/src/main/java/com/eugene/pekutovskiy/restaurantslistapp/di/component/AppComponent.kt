package com.eugene.pekutovskiy.restaurantslistapp.di.component

import com.eugene.pekutovskiy.restaurantslistapp.RestaurantsListApplication
import com.eugene.pekutovskiy.restaurantslistapp.core.datasource.DataSourceModule
import com.eugene.pekutovskiy.restaurantslistapp.core.datasource.local.db.di.RoomProviderModule
import com.eugene.pekutovskiy.restaurantslistapp.di.module.AppModule
import com.eugene.pekutovskiy.restaurantslistapp.di.module.ContributeActivityModule
import com.eugene.pekutovskiy.restaurantslistapp.di.module.ViewModelProviderModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ContributeActivityModule::class,
        ViewModelProviderModule::class,
        RoomProviderModule::class,
        DataSourceModule::class
    ]
)
interface AppComponent {

    fun inject(app: RestaurantsListApplication)

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun application(application: RestaurantsListApplication): Builder
    }
}