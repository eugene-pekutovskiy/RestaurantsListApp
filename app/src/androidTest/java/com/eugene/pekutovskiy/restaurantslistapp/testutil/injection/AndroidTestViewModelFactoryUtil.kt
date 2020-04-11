package com.eugene.pekutovskiy.restaurantslistapp.testutil.injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

fun <T : ViewModel> createFakeViewModelFactory(viewModel: T): ViewModelProvider.Factory {
    return object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(viewModelClass: Class<T>): T {
            if (viewModelClass.isAssignableFrom(viewModel.javaClass)) {
                @Suppress("UNCHECKED_CAST")
                return viewModel as T
            }
            throw IllegalArgumentException("Unknown view model class $viewModelClass")
        }
    }
}