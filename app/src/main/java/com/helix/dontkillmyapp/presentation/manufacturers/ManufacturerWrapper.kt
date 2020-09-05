package com.helix.dontkillmyapp.presentation.manufacturers

import com.helix.dontkillmyapp.data.model.Manufacturer

data class ManufacturerWrapper(
    val manufacturer: Manufacturer,
    val isViewedAlready : Boolean = false
)