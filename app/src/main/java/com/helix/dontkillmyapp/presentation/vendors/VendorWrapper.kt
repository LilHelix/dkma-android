package com.helix.dontkillmyapp.presentation.vendors

import com.helix.dontkillmyapp.data.model.Vendor

data class VendorWrapper(
    val vendor: Vendor,
    val isViewedAlready : Boolean = false
)