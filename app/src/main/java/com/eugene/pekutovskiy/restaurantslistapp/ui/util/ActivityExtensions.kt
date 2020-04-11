package com.eugene.pekutovskiy.restaurantslistapp.ui.util

import android.view.View
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar

fun FragmentActivity.showSnackbar(
    message: String,
    durationFlag: Int = Snackbar.LENGTH_LONG
) {
    val contentView = this.window.decorView.findViewById<View>(android.R.id.content)
    Snackbar.make(contentView, message, durationFlag).show()
}