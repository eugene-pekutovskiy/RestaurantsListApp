package com.eugene.pekutovskiy.restaurantslistapp.testutil.injection

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.test.platform.app.InstrumentationRegistry
import com.eugene.pekutovskiy.restaurantslistapp.RestaurantsListApplication
import com.eugene.pekutovskiy.restaurantslistapp.ui.MainActivity
import dagger.android.AndroidInjector
import dagger.android.AndroidInjector.Factory
import dagger.android.DispatchingAndroidInjector
import dagger.android.DispatchingAndroidInjector_Factory
import javax.inject.Provider

inline fun <reified F : Fragment> createFakeFragmentInjector(crossinline block: F.() -> Unit)
        : DispatchingAndroidInjector<Any> {
    val app = InstrumentationRegistry.getInstrumentation()
        .targetContext.applicationContext as RestaurantsListApplication
    val originalDispatchingActivityInjector = app.dispatchingAndroidInjector
    var originalFragmentInjector: AndroidInjector<Any>? = null
    val fragmentInjector = AndroidInjector<Fragment> { fragment ->
        originalFragmentInjector?.inject(fragment)
        if (fragment is F) {
            fragment.block()
        }
    }
    val fragmentFactory = Factory<Fragment> { fragmentInjector }
    val fragmentMap =
        mapOf(Pair<Class<*>, Provider<Factory<*>>>(F::class.java, Provider { fragmentFactory }))
    val activityInjector = AndroidInjector<Activity> { activity ->
        originalDispatchingActivityInjector.inject(activity)
        if (activity is MainActivity) {
            originalFragmentInjector = activity.androidInjector()
            activity.dispatchingAndroidInjector =
                DispatchingAndroidInjector_Factory.newInstance(fragmentMap, emptyMap())
        }
    }
    val activityFactory = Factory<Activity> { activityInjector }
    val activityMap = mapOf(
        Pair<Class<*>, Provider<Factory<*>>>(
            MainActivity::class.java,
            Provider { activityFactory })
    )
    return DispatchingAndroidInjector_Factory.newInstance(activityMap, emptyMap())
}