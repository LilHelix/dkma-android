package com.helix.dontkillmyapp.data.repository

import com.helix.dontkillmyapp.data.model.Manufacturer
import com.helix.dontkillmyapp.data.model.ManufacturerList
import com.helix.dontkillmyapp.data.remote.ApiService
import javax.inject.Inject

class ManufacturerRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getManufacturers() : ManufacturerList {
        return apiService.getAllManufacturers()
    }
}