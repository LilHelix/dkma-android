package com.helix.dontkillmyapp.data.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.helix.dontkillmyapp.data.model.Manufacturer
import com.helix.dontkillmyapp.presentation.manufacturers.ManufacturerWrapper
import com.helix.dontkillmyapp.utils.SearchQuery
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

private val assembleManufacturersList = { baseManufacturers: List<Manufacturer>,
                                          viewedManufacturers: List<Manufacturer>,
                                          searchQuery: SearchQuery ->
    val actualManufacturers = mutableListOf<ManufacturerWrapper>()
    actualManufacturers
}

@ExperimentalCoroutinesApi
class ManufacturersSearchableDataSourceImpl @Inject constructor() : ManufacturersSearchableDataSource {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val baseManufacturersChannel = ConflatedBroadcastChannel<List<Manufacturer>>()
    private val alreadyViewedManufacturers = ConflatedBroadcastChannel<List<Manufacturer>>()
    private val searchQueryChannel = ConflatedBroadcastChannel<SearchQuery>()

    init {
        scope.launch {
            combine(
                baseManufacturersChannel.asFlow(),
                alreadyViewedManufacturers.asFlow(),
                searchQueryChannel.asFlow()
            ) { baseManufacturers: List<Manufacturer>?, viewedManufacturers: List<Manufacturer>?, searchQuery: SearchQuery? ->
                val actualManufacturers = mutableListOf<ManufacturerWrapper>()
                baseManufacturers?.forEach {
                    actualManufacturers += ManufacturerWrapper(it, viewedManufacturers?.contains(it) == true)
                }
                val result = if (searchQuery == null || searchQuery.isEmpty) {
                    actualManufacturers
                } else {
                    actualManufacturers.filter { it.manufacturer.name.startsWith(searchQuery.query, ignoreCase = true) }
                }

                manufacturersMutable.postValue(result)
            }
        }
    }

    private val manufacturersMutable = MutableLiveData<List<ManufacturerWrapper>>()
    override val manufacturers: LiveData<List<ManufacturerWrapper>> = manufacturersMutable

    override fun setBaseManufacturers(manufacturers: List<Manufacturer>) {
        baseManufacturersChannel.offer(manufacturers)
    }

    override fun onManufacturerViewed(manufacturer: Manufacturer) {
        val alreadyViewed = alreadyViewedManufacturers.valueOrNull?.toMutableList() ?: mutableListOf()
        alreadyViewed += manufacturer
        alreadyViewedManufacturers.offer(alreadyViewed)
    }

    override fun onSearchQueryChange(searchQuery: SearchQuery) {
        searchQueryChannel.offer(searchQuery)
    }

    override fun close() {
        scope.cancel()
    }
}