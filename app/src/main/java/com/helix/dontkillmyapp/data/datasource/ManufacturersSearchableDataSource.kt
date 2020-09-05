package com.helix.dontkillmyapp.data.datasource

import androidx.lifecycle.LiveData
import com.helix.dontkillmyapp.data.model.Manufacturer
import com.helix.dontkillmyapp.presentation.manufacturers.ManufacturerWrapper
import com.helix.dontkillmyapp.utils.SearchQuery

interface ManufacturersSearchableDataSource {
    val manufacturers: LiveData<List<ManufacturerWrapper>>

    fun setBaseManufacturers(manufacturers: List<Manufacturer>)
    fun onManufacturerViewed(manufacturer: Manufacturer)
    fun onSearchQueryChange(searchQuery: SearchQuery)

    fun close()
}