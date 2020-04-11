package com.eugene.pekutovskiy.restaurantslistapp.ui.restaurantsList.mapper

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.eugene.pekutovskiy.restaurantslistapp.R
import com.eugene.pekutovskiy.restaurantslistapp.domain.sort.SortOption
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class StringToSortOptionMapperTest {

    private lateinit var underTest: StringToSortOptionMapper
    private val ctx: Context = ApplicationProvider.getApplicationContext()

    @Before
    fun setUp() {
        underTest = StringToSortOptionMapper(ctx)
    }

    @Test
    fun `test that SortOption Default is returned`() {
        val inputString = ctx.getString(R.string.sort_option_default)
        val result = underTest.map(inputString)
        assertThat(result, `is`(equalTo(SortOption.DEFAULT)))
    }

    @Test
    fun `test that SortOption Best Match is returned`() {
        val inputString = ctx.getString(R.string.sort_option_best_match)
        val result = underTest.map(inputString)
        assertThat(result, `is`(equalTo(SortOption.BEST_MATCH)))
    }

    @Test
    fun `test that SortOption Newest is returned`() {
        val inputString = ctx.getString(R.string.sort_option_newest)
        val result = underTest.map(inputString)
        assertThat(result, `is`(equalTo(SortOption.NEWEST)))
    }

    @Test
    fun `test that SortOption RatingAverage is returned`() {
        val inputString = ctx.getString(R.string.sort_option_rating_average)
        val result = underTest.map(inputString)
        assertThat(result, `is`(equalTo(SortOption.RATING_AVERAGE)))
    }

    @Test
    fun `test that SortOption Distance is returned`() {
        val inputString = ctx.getString(R.string.sort_option_distance)
        val result = underTest.map(inputString)
        assertThat(result, `is`(equalTo(SortOption.DISTANCE)))
    }

    @Test
    fun `test that SortOption Popularity is returned`() {
        val inputString = ctx.getString(R.string.sort_option_popularity)
        val result = underTest.map(inputString)
        assertThat(result, `is`(equalTo(SortOption.POPULARITY)))
    }

    @Test
    fun `test that SortOption Average Product Price is returned`() {
        val inputString = ctx.getString(R.string.sort_option_average_product_price)
        val result = underTest.map(inputString)
        assertThat(result, `is`(equalTo(SortOption.AVERAGE_PRODUCT_PRICE)))
    }

    @Test
    fun `test that SortOption Delivery costs is returned`() {
        val inputString = ctx.getString(R.string.sort_option_delivery_costs)
        val result = underTest.map(inputString)
        assertThat(result, `is`(equalTo(SortOption.DELIVERY_COSTS)))
    }

    @Test
    fun `test that SortOption Minimum Cost is returned`() {
        val inputString = ctx.getString(R.string.sort_option_minimum_cost)
        val result = underTest.map(inputString)
        assertThat(result, `is`(equalTo(SortOption.MINIMUM_COST)))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `assert error is thrown on unknown string input`() {
        val inputString = "unknown string"
        underTest.map(inputString)
    }
}