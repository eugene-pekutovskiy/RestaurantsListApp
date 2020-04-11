package com.eugene.pekutovskiy.restaurantslistapp.domain

import com.eugene.pekutovskiy.restaurantslistapp.domain.sort.SortOption
import com.eugene.pekutovskiy.restaurantslistapp.domain.sort.SortOptionHandler
import com.eugene.pekutovskiy.restaurantslistapp.test_utils.TestDataUtil
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SortOptionHandlerTest {

    private lateinit var underTest: SortOptionHandler
    private val emptySet = HashSet<String>()
    private val favouritesDataSet1 = hashSetOf(
        TestDataUtil.rotiShop.name,
        TestDataUtil.deAmsterdamscheTram.name,
        TestDataUtil.laleRestaurant.name,
        TestDataUtil.tandooriExpress.name,
        TestDataUtil.dailySushi.name
    )
    private val favouritesDataSet2 = hashSetOf(
        TestDataUtil.tanoshiiSushi.name,
        TestDataUtil.sushiOne.name,
        TestDataUtil.deAmsterdamscheTram.name,
        TestDataUtil.tandooriExpress.name,
        TestDataUtil.dailySushi.name
    )

    @Before
    fun setUp() {
        underTest =
            SortOptionHandler()
    }

    @Test
    fun `test with sort option "default", no favourites`() {

        val expectedResult = listOf(
            TestDataUtil.tanoshiiSushi,
            TestDataUtil.sushiOne,
            TestDataUtil.rotiShop,
            TestDataUtil.deAmsterdamscheTram,
            TestDataUtil.royalThai,
            TestDataUtil.laleRestaurant,
            TestDataUtil.tandooriExpress,
            TestDataUtil.dailySushi
        )
        val actualResult = underTest.sort(
            restaurantsData = TestDataUtil.restaurantsData,
            favourites = emptySet,
            sortOption = SortOption.DEFAULT
        )

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `test with sort option "default", first favourites data set`() {

        val expectedResult = listOf(
            TestDataUtil.rotiShop,
            TestDataUtil.deAmsterdamscheTram,
            TestDataUtil.laleRestaurant,
            TestDataUtil.tandooriExpress,
            TestDataUtil.dailySushi,
            TestDataUtil.tanoshiiSushi,
            TestDataUtil.sushiOne,
            TestDataUtil.royalThai
        )

        val actualResult = underTest.sort(
            restaurantsData = TestDataUtil.restaurantsData,
            favourites = favouritesDataSet1,
            sortOption = SortOption.DEFAULT
        )

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `test with sort option "default", second favourites data set`() {

        val expectedResult = listOf(
            TestDataUtil.tanoshiiSushi,
            TestDataUtil.sushiOne,
            TestDataUtil.deAmsterdamscheTram,
            TestDataUtil.tandooriExpress,
            TestDataUtil.dailySushi,
            TestDataUtil.rotiShop,
            TestDataUtil.royalThai,
            TestDataUtil.laleRestaurant
        )

        val actualResult = underTest.sort(
            restaurantsData = TestDataUtil.restaurantsData,
            favourites = favouritesDataSet2,
            sortOption = SortOption.DEFAULT
        )

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `test with sort option "best match", no favourites`() {

        val expectedResult = listOf(
            TestDataUtil.deAmsterdamscheTram,
            TestDataUtil.rotiShop,
            TestDataUtil.sushiOne,
            TestDataUtil.tanoshiiSushi,
            TestDataUtil.laleRestaurant,
            TestDataUtil.royalThai,
            TestDataUtil.dailySushi,
            TestDataUtil.tandooriExpress
        )
        val actualResult = underTest.sort(
            restaurantsData = TestDataUtil.restaurantsData,
            favourites = emptySet,
            sortOption = SortOption.BEST_MATCH
        )

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `test with sort option "best match", favourites are set`() {

        val expectedResult = listOf(
            TestDataUtil.deAmsterdamscheTram,
            TestDataUtil.rotiShop,
            TestDataUtil.laleRestaurant,
            TestDataUtil.dailySushi,
            TestDataUtil.tandooriExpress,
            TestDataUtil.sushiOne,
            TestDataUtil.tanoshiiSushi,
            TestDataUtil.royalThai
        )
        val actualResult = underTest.sort(
            restaurantsData = TestDataUtil.restaurantsData,
            favourites = favouritesDataSet1,
            sortOption = SortOption.BEST_MATCH
        )

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `test with sort option "newest", no favourites`() {

        val expectedResult = listOf(
            TestDataUtil.rotiShop,
            TestDataUtil.sushiOne,
            TestDataUtil.deAmsterdamscheTram,
            TestDataUtil.tanoshiiSushi,
            TestDataUtil.royalThai,
            TestDataUtil.laleRestaurant,
            TestDataUtil.tandooriExpress,
            TestDataUtil.dailySushi

        )
        val actualResult = underTest.sort(
            restaurantsData = TestDataUtil.restaurantsData,
            favourites = emptySet,
            sortOption = SortOption.NEWEST
        )

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `test with sort option "newest", favourites are set`() {

        val expectedResult = listOf(
            TestDataUtil.rotiShop,
            TestDataUtil.deAmsterdamscheTram,
            TestDataUtil.laleRestaurant,
            TestDataUtil.tandooriExpress,
            TestDataUtil.dailySushi,
            TestDataUtil.sushiOne,
            TestDataUtil.tanoshiiSushi,
            TestDataUtil.royalThai
        )
        val actualResult = underTest.sort(
            restaurantsData = TestDataUtil.restaurantsData,
            favourites = favouritesDataSet1,
            sortOption = SortOption.NEWEST
        )

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `test with sort option "rating average", no favourites`() {

        val expectedResult = listOf(
            TestDataUtil.tanoshiiSushi,
            TestDataUtil.rotiShop,
            TestDataUtil.sushiOne,
            TestDataUtil.deAmsterdamscheTram,
            TestDataUtil.royalThai,
            TestDataUtil.laleRestaurant,
            TestDataUtil.tandooriExpress,
            TestDataUtil.dailySushi

        )
        val actualResult = underTest.sort(
            restaurantsData = TestDataUtil.restaurantsData,
            favourites = emptySet,
            sortOption = SortOption.RATING_AVERAGE
        )

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `test with sort option "rating average", favourites are set`() {

        val expectedResult = listOf(
            TestDataUtil.tanoshiiSushi,
            TestDataUtil.sushiOne,
            TestDataUtil.deAmsterdamscheTram,
            TestDataUtil.tandooriExpress,
            TestDataUtil.dailySushi,
            TestDataUtil.rotiShop,
            TestDataUtil.royalThai,
            TestDataUtil.laleRestaurant
        )
        val actualResult = underTest.sort(
            restaurantsData = TestDataUtil.restaurantsData,
            favourites = favouritesDataSet2,
            sortOption = SortOption.RATING_AVERAGE
        )

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `test with sort option "distance", no favourites`() {

        val expectedResult = listOf(
            TestDataUtil.tanoshiiSushi,
            TestDataUtil.sushiOne,
            TestDataUtil.rotiShop,
            TestDataUtil.deAmsterdamscheTram,
            TestDataUtil.royalThai,
            TestDataUtil.laleRestaurant,
            TestDataUtil.dailySushi,
            TestDataUtil.tandooriExpress

        )
        val actualResult = underTest.sort(
            restaurantsData = TestDataUtil.restaurantsData,
            favourites = emptySet,
            sortOption = SortOption.DISTANCE
        )

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `test with sort option "popularity", no favourites`() {

        val expectedResult = listOf(
            TestDataUtil.rotiShop,
            TestDataUtil.sushiOne,
            TestDataUtil.tanoshiiSushi,
            TestDataUtil.deAmsterdamscheTram,
            TestDataUtil.royalThai,
            TestDataUtil.laleRestaurant,
            TestDataUtil.tandooriExpress,
            TestDataUtil.dailySushi
        )
        val actualResult = underTest.sort(
            restaurantsData = TestDataUtil.restaurantsData,
            favourites = emptySet,
            sortOption = SortOption.POPULARITY
        )

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `test with sort option "average product price", no favourites`() {

        val expectedResult = listOf(
            TestDataUtil.deAmsterdamscheTram,
            TestDataUtil.rotiShop,
            TestDataUtil.sushiOne,
            TestDataUtil.tanoshiiSushi,
            TestDataUtil.laleRestaurant,
            TestDataUtil.royalThai,
            TestDataUtil.tandooriExpress,
            TestDataUtil.dailySushi
        )
        val actualResult = underTest.sort(
            restaurantsData = TestDataUtil.restaurantsData,
            favourites = emptySet,
            sortOption = SortOption.AVERAGE_PRODUCT_PRICE
        )

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `test with sort option "delivery cost", no favourites`() {

        val expectedResult = listOf(
            TestDataUtil.sushiOne,
            TestDataUtil.rotiShop,
            TestDataUtil.deAmsterdamscheTram,
            TestDataUtil.tanoshiiSushi,
            TestDataUtil.laleRestaurant,
            TestDataUtil.royalThai,
            TestDataUtil.tandooriExpress,
            TestDataUtil.dailySushi
        )
        val actualResult = underTest.sort(
            restaurantsData = TestDataUtil.restaurantsData,
            favourites = emptySet,
            sortOption = SortOption.DELIVERY_COSTS
        )

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `test with sort option "minimum cost", no favourites`() {

        val expectedResult = listOf(
            TestDataUtil.deAmsterdamscheTram,
            TestDataUtil.tanoshiiSushi,
            TestDataUtil.sushiOne,
            TestDataUtil.rotiShop,
            TestDataUtil.laleRestaurant,
            TestDataUtil.royalThai,
            TestDataUtil.dailySushi,
            TestDataUtil.tandooriExpress
        )
        val actualResult = underTest.sort(
            restaurantsData = TestDataUtil.restaurantsData,
            favourites = emptySet,
            sortOption = SortOption.MINIMUM_COST
        )

        assertEquals(expectedResult, actualResult)
    }
}