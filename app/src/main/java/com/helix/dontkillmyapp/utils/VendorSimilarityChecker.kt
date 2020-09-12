package com.helix.dontkillmyapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.helix.dontkillmyapp.presentation.vendors.VendorWrapper

object VendorSimilarityChecker : SimilarityChecker<VendorWrapper> {

    override fun provideItemCallback(): DiffUtil.ItemCallback<VendorWrapper> {
        return object: DiffUtil.ItemCallback<VendorWrapper>() {
            override fun areItemsTheSame(oldItem: VendorWrapper, newItem: VendorWrapper): Boolean {
                return oldItem.vendor.name == newItem.vendor.name
            }

            override fun areContentsTheSame(oldItem: VendorWrapper, newItem: VendorWrapper): Boolean {
                return oldItem == newItem
            }

        }
    }
}