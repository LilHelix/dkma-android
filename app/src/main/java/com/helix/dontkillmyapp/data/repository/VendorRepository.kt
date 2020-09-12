package com.helix.dontkillmyapp.data.repository

import com.helix.dontkillmyapp.data.model.VendorList
import com.helix.dontkillmyapp.data.remote.ApiService
import javax.inject.Inject

class VendorRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getVendors() : VendorList {
        return apiService.getAllVendors()
    }
}