package com.helix.dontkillmyapp.extensions

object Strings {
    const val EMPTY = ""
}

fun CharSequence?.orEmpty() : CharSequence = this ?: Strings.EMPTY