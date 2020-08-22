package com.helix.dontkillmyapp.data.remote

import com.helix.dontkillmyapp.data.model.ManufacturerList
import retrofit2.http.GET

const val ENDPOINT = "https://dontkillmyapp.com/"

interface ApiService {

    /**
     * Получает данные по всем известным вендорам.
     */
    @GET("api/v1/output.json")
    suspend fun getAllManufacturers() : ManufacturerList

}
