package com.helix.dontkillmyapp.data.holders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.helix.dontkillmyapp.data.model.Vendor
import com.helix.dontkillmyapp.presentation.vendors.VendorWrapper
import com.helix.dontkillmyapp.utils.SearchQuery
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias Manufacturers = List<Vendor>
typealias WrappedManufacturers = List<VendorWrapper>

private val searchCombiner : suspend (Manufacturers, Manufacturers, SearchQuery) -> WrappedManufacturers =
    { baseVendors: List<Vendor>,
      viewedVendors: List<Vendor>,
      searchQuery: SearchQuery ->
        val actualManufacturers = mutableListOf<VendorWrapper>()
        baseVendors.forEach {
            actualManufacturers += VendorWrapper(it, viewedVendors.contains(it))
        }
        if (searchQuery.isEmpty) {
            actualManufacturers
        } else {
            actualManufacturers.filter { it.vendor.name.startsWith(searchQuery.query, ignoreCase = true) }
        }
}

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class VendorsDataHolderImpl @Inject constructor() : VendorsDataHolder {

    private val customScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val baseManufacturersStateFlow =
        MutableStateFlow(emptyList<Vendor>())
    private val alreadyViewedManufacturersStateFlow =
        MutableStateFlow(emptyList<Vendor>())
    private val searchQueryStateFlow =
        MutableStateFlow(SearchQuery.Queries.EMPTY)

    private val manufacturersMutable = MutableLiveData<List<VendorWrapper>>()

    init {
        customScope.launch {
            combine(
                baseManufacturersStateFlow,
                alreadyViewedManufacturersStateFlow,
                searchQueryStateFlow,
                searchCombiner
            ).collect(object: FlowCollector<List<VendorWrapper>> {
                override suspend fun emit(value: List<VendorWrapper>) {
                    manufacturersMutable.postValue(value)
                }
            })
        }
    }

    override val vendors: LiveData<List<VendorWrapper>> = manufacturersMutable

    override fun setVendors(vendors: List<Vendor>) {
        baseManufacturersStateFlow.value = vendors
    }

    override fun setVendorViewed(vendor: Vendor) {
        val alreadyViewed = alreadyViewedManufacturersStateFlow.value.toMutableList()
        alreadyViewed += vendor
        alreadyViewedManufacturersStateFlow.value = alreadyViewed
    }

    override fun onSearchQueryChange(searchQuery: SearchQuery) {
        searchQueryStateFlow.value = searchQuery
    }

    override fun close() {
        customScope.cancel()
    }
}