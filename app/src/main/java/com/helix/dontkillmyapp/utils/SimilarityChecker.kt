package com.helix.dontkillmyapp.utils

import androidx.recyclerview.widget.DiffUtil

interface SimilarityChecker<T> {

    fun provideItemCallback() : DiffUtil.ItemCallback<T>
}

