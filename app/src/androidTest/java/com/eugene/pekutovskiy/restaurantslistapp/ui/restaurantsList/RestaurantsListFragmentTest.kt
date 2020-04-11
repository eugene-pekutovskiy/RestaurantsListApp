package com.eugene.pekutovskiy.restaurantslistapp.ui.restaurantsList

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.eugene.pekutovskiy.restaurantslistapp.R
import com.eugene.pekutovskiy.restaurantslistapp.RestaurantsListApplication
import com.eugene.pekutovskiy.restaurantslistapp.testutil.AndroidTestDataUtil
import com.eugene.pekutovskiy.restaurantslistapp.testutil.injection.createFakeFragmentInjector
import com.eugene.pekutovskiy.restaurantslistapp.testutil.injection.createFakeViewModelFactory
import com.eugene.pekutovskiy.restaurantslistapp.testutil.matchers.RecyclerViewMatcher
import com.eugene.pekutovskiy.restaurantslistapp.ui.MainActivity
import com.eugene.pekutovskiy.restaurantslistapp.ui.restaurantsList.adapter.RestaurantsListAdapter
import com.eugene.pekutovskiy.restaurantslistapp.ui.restaurantsList.adapter.RestaurantsListDiffCallback
import com.eugene.pekutovskiy.restaurantslistapp.ui.restaurantsList.uimodel.RestaurantUiModel
import org.hamcrest.Matchers.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RestaurantsListFragmentTest {

    private val appContext = InstrumentationRegistry.getInstrumentation()
        .targetContext.applicationContext as RestaurantsListApplication

    @Mock
    lateinit var viewModel: RestaurantsListViewModel
    val adapter = RestaurantsListAdapter(RestaurantsListDiffCallback())
    val uiEventLiveData = MutableLiveData<RestaurantsListViewModel.UiEvent>()

    @Rule
    @JvmField
    val activityRule =
        object : ActivityTestRule<MainActivity>(MainActivity::class.java, false, false) {
            override fun beforeActivityLaunched() {
                super.beforeActivityLaunched()
                appContext.dispatchingAndroidInjector =
                    createFakeFragmentInjector<RestaurantsListFragment> {
                        this.viewModelFactory =
                            createFakeViewModelFactory(
                                viewModel
                            )
                        this.restaurantsListAdapter = adapter
                    }
            }
        }

    @Before
    fun setUp() {
        val arrangeBuilder = ArrangeBuilder()
        arrangeBuilder.substituteLiveData()
        activityRule.launchActivity(null)
        val items = AndroidTestDataUtil.restaurantsList
        arrangeBuilder.postUpdateRestaurantsListEvent(items)
    }

    @Test
    fun testRestaurantsListIsDisplayed() {

        onView(withId(R.id.restaurantsList)).check(matches(isDisplayed()))
        onView(withId(R.id.restaurantsList)).check(
            matches(
                RecyclerViewMatcher.hasItemsCount(
                    AndroidTestDataUtil.restaurantsList.size
                )
            )
        )

        assertThat(
            activityRule.activity.title.toString(),
            `is`(equalTo(appContext.getString(R.string.restaurants_list)))
        )

    }

    @Test
    fun testItemsAreDisplayedInTheSameOrderAsWerePassedIntoUiEvent() {
        //verify default sort option is selected
        onView(withId(android.R.id.text1)).check(
            matches(
                withText(
                    appContext.getString(R.string.sort_option_default)
                )
            )
        )

        val expectedItemsOrder: List<String> = listOf(
            AndroidTestDataUtil.tanoshiSushi.name,
            AndroidTestDataUtil.dmsterdamscheTram.name,
            AndroidTestDataUtil.laleRestaurantUiModel.name,
            AndroidTestDataUtil.tandooriExpress.name,
            AndroidTestDataUtil.dailySushi.name
        )

        verifyItemsOrder(expectedItemsOrder)
    }

    @Test
    fun verifyGetRestaurantsDataIsBeingCalledWhenSpinnerOptionIsClicked() {
        val stringSortOption = appContext.getString(R.string.sort_option_rating_average)
        onView(withId(R.id.spinner)).perform(click())
        onView(withText(stringSortOption)).perform(click())
        Mockito.verify(viewModel).getRestaurantsData(stringSortOption)
    }

    @Test
    fun testProgressBarIsBeingDisplayedWhileAddingToFavourites() {
        val itemPosition = 0
        val itemToFavourite = AndroidTestDataUtil.restaurantsList[itemPosition].name
        onView(
            RecyclerViewMatcher.atPositionOnView(
                R.id.restaurantsList,
                itemPosition,
                R.id.addToFavouritesButton
            )
        ).perform(click())

        Mockito.verify(viewModel).addToFavourites(itemToFavourite)
        //verify addToFavouritesButton is no longer displayed
        onView(
            RecyclerViewMatcher.atPositionOnView(
                R.id.restaurantsList,
                itemPosition,
                R.id.addToFavouritesButton
            )
        ).check(matches(not(isDisplayed())))
        // verify progress bar is visible
        onView(
            RecyclerViewMatcher.atPositionOnView(
                R.id.restaurantsList,
                itemPosition,
                R.id.progress
            )
        ).check(matches(isDisplayed()))
    }

    // ....

    private fun verifyItemsOrder(restaurantNames: List<String>) {
        restaurantNames.forEachIndexed { index, element ->
            onView(
                RecyclerViewMatcher.atPositionOnView(
                    R.id.restaurantsList,
                    index,
                    R.id.restaurantName
                )
            )
                .check(matches(withText(element)))
        }
    }

    private inner class ArrangeBuilder {
        fun substituteLiveData(): ArrangeBuilder {
            Mockito.`when`(viewModel.uiEventLiveData).thenReturn(uiEventLiveData)
            return this
        }

        fun postUpdateRestaurantsListEvent(items: List<RestaurantUiModel>) {
            uiEventLiveData.postValue(
                RestaurantsListViewModel.UiEvent.UpdateRestaurantsList(
                    items
                )
            )
        }
    }
}