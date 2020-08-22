package com.helix.dontkillmyapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.helix.dontkillmyapp.data.model.Manufacturer

object DeviceManufacturerSimilarityChecker : SimilarityChecker<Manufacturer> {

    override fun provideItemCallback(): DiffUtil.ItemCallback<Manufacturer> {
        return object: DiffUtil.ItemCallback<Manufacturer>() {
            override fun areItemsTheSame(oldItem: Manufacturer, newItem: Manufacturer): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Manufacturer, newItem: Manufacturer): Boolean {
                return oldItem == newItem
            }

        }
    }
}