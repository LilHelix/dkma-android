package com.helix.dontkillmyapp.extensions

import android.view.View
import android.widget.TextView
import androidx.annotation.DrawableRes

fun TextView.setDrawable(
    @DrawableRes left: Int? = null,
    @DrawableRes top: Int? = null,
    @DrawableRes right: Int? = null,
    @DrawableRes bottom: Int? = null
) {
    if (left == null && top == null && right == null && bottom == null) {
        throw NullPointerException("Please provide at least one drawable")
    }

    setCompoundDrawablesWithIntrinsicBounds(left ?: 0, top ?: 0, right ?: 0, bottom ?: 0)
}