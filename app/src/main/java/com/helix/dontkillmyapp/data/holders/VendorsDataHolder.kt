package com.helix.dontkillmyapp.data.holders

import androidx.lifecycle.LiveData
import com.helix.dontkillmyapp.data.model.Vendor
import com.helix.dontkillmyapp.presentation.vendors.VendorWrapper
import com.helix.dontkillmyapp.utils.SearchQuery

interface VendorsDataHolder {
    val vendors: LiveData<List<VendorWrapper>>

    fun setVendors(vendors: List<Vendor>)
    fun setVendorViewed(vendor: Vendor)
    fun onSearchQueryChange(searchQuery: SearchQuery)

    fun close()
}