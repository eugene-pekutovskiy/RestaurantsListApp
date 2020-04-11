package com.eugene.pekutovskiy.restaurantslistapp.ui.util

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes

fun View.visibleIf(show: Boolean, invisibilityFlag: Int = View.GONE) {
    this.visibility = if (show) View.VISIBLE else invisibilityFlag
}

fun Context.showToast(@StringRes message: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}