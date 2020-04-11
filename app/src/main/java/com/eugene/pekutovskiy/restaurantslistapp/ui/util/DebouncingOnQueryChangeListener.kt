package com.eugene.pekutovskiy.restaurantslistapp.ui.util

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DebouncingOnQueryChangeListener(
    lifecycle: Lifecycle,
    private val callback: (String) -> Unit
) : SearchView.OnQueryTextListener {

    private val coroutineScope = lifecycle.coroutineScope
    private var job: Job? = null

    override fun onQueryTextSubmit(query: String?) = false

    override fun onQueryTextChange(newText: String?): Boolean {
        job?.cancel()
        job = coroutineScope.launch {
            newText?.let {
                delay(DELAY_IN_MILLIS)
                callback(it)
            }
        }
        return true
    }

    companion object {
        const val DELAY_IN_MILLIS = 500L
    }
}