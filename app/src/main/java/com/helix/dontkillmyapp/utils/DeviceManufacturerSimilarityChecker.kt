package com.helix.dontkillmyapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.helix.dontkillmyapp.data.model.Manufacturer
import com.helix.dontkillmyapp.presentation.manufacturers.ManufacturerWrapper

object DeviceManufacturerSimilarityChecker : SimilarityChecker<ManufacturerWrapper> {

    override fun provideItemCallback(): DiffUtil.ItemCallback<ManufacturerWrapper> {
        return object: DiffUtil.ItemCallback<ManufacturerWrapper>() {
            override fun areItemsTheSame(oldItem: ManufacturerWrapper, newItem: ManufacturerWrapper): Boolean {
                return oldItem.manufacturer.name == newItem.manufacturer.name
            }

            override fun areContentsTheSame(oldItem: ManufacturerWrapper, newItem: ManufacturerWrapper): Boolean {
                return oldItem == newItem
            }

        }
    }
}