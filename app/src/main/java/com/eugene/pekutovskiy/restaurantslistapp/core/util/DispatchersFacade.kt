package com.eugene.pekutovskiy.restaurantslistapp.core.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class DispatchersFacade @Inject constructor() {

    fun io(): CoroutineDispatcher = Dispatchers.IO

    fun computation(): CoroutineDispatcher = Dispatchers.Default

    fun main(): CoroutineDispatcher = Dispatchers.Main
}