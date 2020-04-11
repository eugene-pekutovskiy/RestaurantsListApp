package com.eugene.pekutovskiy.restaurantslistapp.ui.util.listeners

import android.view.MenuItem

abstract class OnActionExpandListener :  MenuItem.OnActionExpandListener {
    override fun onMenuItemActionExpand(item: MenuItem?): Boolean = true
}