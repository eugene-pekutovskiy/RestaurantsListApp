package com.eugene.pekutovskiy.restaurantslistapp.ui.restaurantsList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.eugene.pekutovskiy.restaurantslistapp.domain.Result
import com.eugene.pekutovskiy.restaurantslistapp.domain.sort.SortOption
import com.eugene.pekutovskiy.restaurantslistapp.domain.usecase.AddToFavouritesUseCase
import com.eugene.pekutovskiy.restaurantslistapp.domain.usecase.GetRestaurantsDataUseCase
import com.eugene.pekutovskiy.restaurantslistapp.domain.usecase.RemoveFromFavouritesUseCase
import com.eugene.pekutovskiy.restaurantslistapp.test_utils.MainCoroutineRule
import com.eugene.pekutovskiy.restaurantslistapp.test_utils.TestDataUtil
import com.eugene.pekutovskiy.restaurantslistapp.ui.restaurantsList.RestaurantsListViewModel.UiEvent
import com.eugene.pekutovskiy.restaurantslistapp.ui.restaurantsList.mapper.StringToSortOptionMapper
import com.eugene.pekutovskiy.restaurantslistapp.ui.restaurantsList.uimodel.RestaurantUiModel
import com.nhaarman.mockitokotlin2.only
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsInstanceOf.instanceOf
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.*

@ExperimentalCoroutinesApi
class RestaurantsListViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
    @Mock
    private lateinit var getRestaurantsDataUseCase: GetRestaurantsDataUseCase
    @Mock
    private lateinit var addToFavouritesUseCase: AddToFavouritesUseCase
    @Mock
    private lateinit var removeFromFavouritesUseCase: RemoveFromFavouritesUseCase
    @Mock
    private lateinit var stringToSortOptionMapper: StringToSortOptionMapper

    private lateinit var underTest: RestaurantsListViewModel

    private val stringOption = "Default"
    private val sortOption = SortOption.DEFAULT
    private val restaurantName = TestDataUtil.laleRestaurant.name
    private val expectedException: Throwable = RuntimeException()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        underTest = RestaurantsListViewModel(
            getRestaurantsDataUseCase,
            addToFavouritesUseCase,
            removeFromFavouritesUseCase,
            stringToSortOptionMapper
        )
    }

    @Test
    fun `test getting restaurants data if result success`() = runBlockingTest {
        val expectedItems: List<RestaurantUiModel> =
            Collections.singletonList(TestDataUtil.laleRestaurantUiModel)

        ArrangeBuilder()
            .withSortOption(stringOption, sortOption)
            .withRestaurantsResult(sortOption, Result.Success(expectedItems))

        underTest.getRestaurantsData(stringOption)

        verify(stringToSortOptionMapper).map(stringOption)
        verify(getRestaurantsDataUseCase).execute(sortOption)
        assertThat(
            underTest.uiEventLiveData.value,
            instanceOf(UiEvent.UpdateRestaurantsList::class.java)
        )
        assertThat(
            (underTest.uiEventLiveData.value as UiEvent.UpdateRestaurantsList).items,
            equalTo(expectedItems)
        )
    }

    @Test
    fun `test getting restaurants data if result error`() = runBlockingTest {
        ArrangeBuilder()
            .withSortOption(stringOption, sortOption)
            .withRestaurantsResult(sortOption, Result.Error(expectedException))

        underTest.getRestaurantsData(stringOption)

        verify(stringToSortOptionMapper).map(stringOption)
        verify(getRestaurantsDataUseCase).execute(sortOption)

        assertErrorData()
    }

    @Test
    fun `test adding to favourites if result success`() = runBlockingTest {
        ArrangeBuilder()
            .withAddingToFavouritesResult(restaurantName, Result.Success(data = Unit))

        underTest.addToFavourites(restaurantName)

        verify(addToFavouritesUseCase).execute(restaurantName)

        assertThat(
            underTest.uiEventLiveData.value,
            instanceOf(UiEvent.AddedToFavourites::class.java)
        )
    }

    @Test
    fun `test adding to favourites if result error`() = runBlockingTest {
        ArrangeBuilder()
            .withAddingToFavouritesResult(restaurantName, Result.Error(expectedException))

        underTest.addToFavourites(restaurantName)

        verify(addToFavouritesUseCase).execute(restaurantName)

        assertErrorData()
    }

    @Test
    fun `test remove from favourites if result success`() = runBlockingTest {
        ArrangeBuilder()
            .withRemoveFromFavouritesResult(restaurantName, Result.Success(data = Unit))

        underTest.removeFromFavourites(restaurantName)

        verify(removeFromFavouritesUseCase).execute(restaurantName)

        assertThat(
            underTest.uiEventLiveData.value,
            instanceOf(UiEvent.RemovedFromFavourites::class.java)
        )
    }

    @Test
    fun `test remove from favourites if result error`() = runBlockingTest {
        ArrangeBuilder()
            .withRemoveFromFavouritesResult(restaurantName, Result.Error(expectedException))

        underTest.removeFromFavourites(restaurantName)

        verify(removeFromFavouritesUseCase).execute(restaurantName)

        assertErrorData()
    }

    @Test
    fun `test filterByRestaurantName if query is blank`() = runBlockingTest {
        val query = "  "
        underTest.filterByRestaurantName(query, stringOption)
        verifyZeroInteractions(stringToSortOptionMapper)
        verifyZeroInteractions(getRestaurantsDataUseCase)
    }

    @Test
    fun `test filterByRestaurantName if previous query the same as current one`() =
        runBlockingTest {
            val query = "Lale"
            val expectedItems: List<RestaurantUiModel> =
                Collections.singletonList(TestDataUtil.laleRestaurantUiModel)

            ArrangeBuilder()
                .withSortOption(stringOption, sortOption)
                .withRestaurantsResult(sortOption, Result.Success(expectedItems))
            //assert initial value of the filteringByNameQuery is null
            assertNull(underTest.filteringByNameQuery)

            //filter and assert that the list is filtered successfully
            underTest.filterByRestaurantName(query, stringOption)

            assertThat(
                underTest.uiEventLiveData.value,
                instanceOf(UiEvent.UpdateRestaurantsList::class.java)
            )

            assertEquals(query, underTest.filteringByNameQuery)

            // filter one more time with the same query
            underTest.filterByRestaurantName(query, stringOption)

            verify(stringToSortOptionMapper, only()).map(stringOption)
            verify(getRestaurantsDataUseCase, only()).execute(sortOption)
        }

    @Test
    fun `test filterByRestaurantName if previous query is null`() =
        runBlockingTest {
            val query = "Lale"
            val expectedItems: List<RestaurantUiModel> =
                Collections.singletonList(TestDataUtil.laleRestaurantUiModel)

            ArrangeBuilder()
                .withSortOption(stringOption, sortOption)
                .withRestaurantsResult(sortOption, Result.Success(expectedItems))
            //assert initial value of the filteringByNameQuery is null
            assertNull(underTest.filteringByNameQuery)

            underTest.filterByRestaurantName(query, stringOption)

            verify(stringToSortOptionMapper).map(stringOption)
            verify(getRestaurantsDataUseCase).execute(sortOption)

            assertEquals(query, underTest.filteringByNameQuery)
            assertThat(
                underTest.uiEventLiveData.value,
                instanceOf(UiEvent.UpdateRestaurantsList::class.java)
            )
            assertThat(
                (underTest.uiEventLiveData.value as UiEvent.UpdateRestaurantsList).items,
                equalTo(expectedItems)
            )
        }

    @Test
    fun `test filterByRestaurantName if no matches found`() =
        runBlockingTest {
            val query = "asdf"

            ArrangeBuilder()
                .withSortOption(stringOption, sortOption)
                .withRestaurantsResult(sortOption, Result.Error(expectedException))

            underTest.filterByRestaurantName(query, stringOption)

            verify(stringToSortOptionMapper).map(stringOption)
            verify(getRestaurantsDataUseCase).execute(sortOption)

            assertErrorData()
        }

    @Test
    fun `test filterByRestaurantName if error result`() =
        runBlockingTest {
            val query = "Lale"
            val expectedItems: List<RestaurantUiModel> = emptyList()

            ArrangeBuilder()
                .withSortOption(stringOption, sortOption)
                .withRestaurantsResult(sortOption, Result.Success(expectedItems))

            underTest.filterByRestaurantName(query, stringOption)

            verify(stringToSortOptionMapper).map(stringOption)
            verify(getRestaurantsDataUseCase).execute(sortOption)

            assertThat(
                underTest.uiEventLiveData.value,
                instanceOf(UiEvent.NoMatchesFound::class.java)
            )
        }

    private fun assertErrorData() {
        assertThat(
            underTest.uiEventLiveData.value,
            instanceOf(UiEvent.Error::class.java)
        )
        assertThat(
            (underTest.uiEventLiveData.value as UiEvent.Error).error,
            equalTo(expectedException)
        )
    }


    private inner class ArrangeBuilder {
        fun withSortOption(stringOption: String, sortOption: SortOption): ArrangeBuilder {
            whenever(stringToSortOptionMapper.map(stringOption)).thenReturn(sortOption)
            return this
        }

        suspend fun withRestaurantsResult(
            sortOption: SortOption,
            result: Result<List<RestaurantUiModel>>
        ): ArrangeBuilder {
            whenever(getRestaurantsDataUseCase.execute(sortOption)).thenReturn(result)
            return this
        }

        suspend fun withAddingToFavouritesResult(
            restaurantName: String,
            result: Result<Unit>
        ): ArrangeBuilder {
            whenever(addToFavouritesUseCase.execute(restaurantName)).thenReturn(result)
            return this
        }

        suspend fun withRemoveFromFavouritesResult(
            restaurantName: String,
            result: Result<Unit>
        ): ArrangeBuilder {
            whenever(removeFromFavouritesUseCase.execute(restaurantName)).thenReturn(result)
            return this
        }
    }

}