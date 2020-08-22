package com.helix.dontkillmyapp.data.remote

import com.helix.dontkillmyapp.data.model.ManufacturerList
import retrofit2.http.GET

const val ENDPOINT = "https://dontkillmyapp.com/api/"

interface ApiService {

    /**
     * Получает данные по всем известным вендорам.
     */
    @GET("v1/output.json")
    suspend fun getAllManufacturers() : ManufacturerList

}
