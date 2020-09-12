package com.helix.dontkillmyapp.presentation.vendors

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helix.dontkillmyapp.data.holders.VendorsDataHolder
import com.helix.dontkillmyapp.data.model.Vendor
import com.helix.dontkillmyapp.data.usecase.GetVendorsUseCase
import com.helix.dontkillmyapp.navigation.CustomRouter
import com.helix.dontkillmyapp.presentation.vendordetails.VendorDetailsScreen
import com.helix.dontkillmyapp.presentation.theme.Theme
import com.helix.dontkillmyapp.presentation.theme.ThemeHelper
import com.helix.dontkillmyapp.utils.OpState
import com.helix.dontkillmyapp.utils.SearchQuery
import com.helix.dontkillmyapp.utils.progressive
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class VendorListViewModel @ViewModelInject constructor(
    private val getVendorsUseCase: GetVendorsUseCase,
    private val vendorsDataHolder: VendorsDataHolder,
    private val router: CustomRouter,
    private val themeHelper: ThemeHelper
) : ViewModel() {

    private val vendorListMutableLiveData = vendorsDataHolder.vendors
    val vendorListLiveData : LiveData<List<VendorWrapper>> = vendorListMutableLiveData

    private val getVendorsUseCaseState = MutableLiveData<OpState>()
    val getVendorsUseCaseStateLiveData : LiveData<OpState> = getVendorsUseCaseState

    fun getManufacturers() {
        viewModelScope.launch {
            progressive {
                getVendorsUseCase.execute()
            }.collect {
                getVendorsUseCaseState.postValue(it.state)
                it.doOnSuccess { manufacturers ->
                    vendorsDataHolder.setVendors(manufacturers)
                }
            }
        }
    }

    fun openManufacturer(vendor: Vendor) {
        vendorsDataHolder.setVendorViewed(vendor)
        router.showAtop(VendorDetailsScreen(vendor))
    }

    fun filter(searchQuery: SearchQuery) {
        vendorsDataHolder.onSearchQueryChange(searchQuery)
    }

    fun changeTheme(theme: Theme) {
        themeHelper.changeTheme(theme)
    }

    override fun onCleared() {
        vendorsDataHolder.close()
        super.onCleared()
    }
}