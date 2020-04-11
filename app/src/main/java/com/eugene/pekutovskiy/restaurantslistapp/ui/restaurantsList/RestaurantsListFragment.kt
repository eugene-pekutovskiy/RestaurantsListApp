package com.eugene.pekutovskiy.restaurantslistapp.ui.restaurantsList

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.eugene.pekutovskiy.restaurantslistapp.R
import com.eugene.pekutovskiy.restaurantslistapp.ui.restaurantsList.RestaurantsListViewModel.UiEvent
import com.eugene.pekutovskiy.restaurantslistapp.ui.restaurantsList.adapter.RestaurantsListAdapter
import com.eugene.pekutovskiy.restaurantslistapp.ui.util.DebouncingOnQueryChangeListener
import com.eugene.pekutovskiy.restaurantslistapp.ui.util.listeners.OnActionExpandListener
import com.eugene.pekutovskiy.restaurantslistapp.ui.util.listeners.OnSpinnerItemSelectedListener
import com.eugene.pekutovskiy.restaurantslistapp.ui.util.showSnackbar
import com.eugene.pekutovskiy.restaurantslistapp.ui.util.showToast
import com.eugene.pekutovskiy.restaurantslistapp.ui.util.visibleIf
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_restaurants_list.*
import javax.inject.Inject

class RestaurantsListFragment : Fragment(R.layout.fragment_restaurants_list) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var restaurantsListAdapter: RestaurantsListAdapter

    private lateinit var viewModel: RestaurantsListViewModel
    private val handlerDelegate = lazy(LazyThreadSafetyMode.NONE, ::Handler)
    private val lazyHandler by handlerDelegate

    private val onSpinnerItemSelectedListener = object : OnSpinnerItemSelectedListener<String>() {
        override fun onItemSelected(item: String) {
            viewModel.getRestaurantsData(item)
            scrollTop()
        }
    }

    private val onActionExpandListener = object : OnActionExpandListener() {
        override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
            refreshRestaurantsData()
            return true
        }
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        viewModel = ViewModelProvider(this, viewModelFactory).get(
            RestaurantsListViewModel::class.java
        )
        viewModel.uiEventLiveData.observe(
            viewLifecycleOwner, Observer(::handleUiEvent)
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        val menuItem = menu.findItem(R.id.action_filter)
        menuItem.setOnActionExpandListener(onActionExpandListener)

        val searchView = menuItem.actionView as? SearchView
        searchView?.apply {
            queryHint = getString(R.string.filter_by_restaurant_name)
            setOnQueryTextListener(DebouncingOnQueryChangeListener(lifecycle) {
                val stringSortOption = spinner.selectedItem as String
                viewModel.filterByRestaurantName(it, stringSortOption)
            })
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPause() {
        if (handlerDelegate.isInitialized()) {
            lazyHandler.removeCallbacksAndMessages(null)
        }
        super.onPause()
    }

    private fun setupUi() {
        requireActivity().title = getString(R.string.restaurants_list)
        restaurantsListAdapter.onClickListener = { position ->
            val item = restaurantsListAdapter.getItem(position)
            val name = item.name
            if (item.isFavourite) {
                viewModel.removeFromFavourites(name)
            } else {
                viewModel.addToFavourites(name)
            }
        }

        restaurantsList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = restaurantsListAdapter
        }

        spinner.apply {
            adapter = ArrayAdapter(
                requireContext(),
                R.layout.spinner_item,
                resources.getStringArray(R.array.sortOptions_arr).toList()
            )
            onItemSelectedListener = onSpinnerItemSelectedListener
        }
    }

    private fun handleUiEvent(uiEvent: UiEvent) {
        when (uiEvent) {
            is UiEvent.ShowLoading -> {
                showProgress(show = true)
            }
            is UiEvent.Error -> {
                showProgress(show = false)
                showNoMatchesFoundText(show = false)
                requireActivity().showSnackbar(
                    uiEvent.error.message ?: getString(R.string.unknown_error_txt)
                )
            }
            is UiEvent.UpdateRestaurantsList -> {
                showProgress(show = false)
                showNoMatchesFoundText(show = false)
                val restaurants = uiEvent.items
                restaurantsListAdapter.submitList(restaurants)
            }

            is UiEvent.AddedToFavourites -> {
                refreshRestaurantsData()
                requireContext().showToast(R.string.restaurant_has_been_added_to_favourites)
                scrollTop()
            }

            is UiEvent.RemovedFromFavourites -> {
                refreshRestaurantsData()
                requireContext().showToast(R.string.restaurant_has_been_removed_from_favourites)
            }

            is UiEvent.NoMatchesFound -> {
                showNoMatchesFoundText(show = true)
                restaurantsListAdapter.submitList(emptyList())
            }
        }
    }

    private fun refreshRestaurantsData() {
        val selectedItem = spinner.selectedItem as String
        viewModel.getRestaurantsData(selectedItem)
    }

    private fun showProgress(show: Boolean) {
        progressBar.visibleIf(show)
    }

    private fun showNoMatchesFoundText(show: Boolean) {
        noMatchesFoundText.visibleIf(show)
    }

    private fun scrollTop() {
        lazyHandler.postDelayed({ restaurantsList.scrollToPosition(0) }, 200)
    }
}