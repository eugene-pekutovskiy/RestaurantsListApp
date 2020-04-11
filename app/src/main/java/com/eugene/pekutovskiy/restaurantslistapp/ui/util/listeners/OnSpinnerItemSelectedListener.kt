package com.eugene.pekutovskiy.restaurantslistapp.ui.util.listeners

import android.view.View
import android.widget.AdapterView

@Suppress("UNCHECKED_CAST")
abstract class OnSpinnerItemSelectedListener<T> : AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(parent: AdapterView<*>?) {
        // no opt
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val itemAtPosition = parent?.getItemAtPosition(position) as T
        onItemSelected(itemAtPosition)
    }

    abstract fun onItemSelected(item: T)
}