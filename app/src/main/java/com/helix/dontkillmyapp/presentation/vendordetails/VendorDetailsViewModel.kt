package com.helix.dontkillmyapp.presentation.vendordetails

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.helix.dontkillmyapp.data.model.Vendor
import com.helix.dontkillmyapp.extensions.requireValue
import com.helix.dontkillmyapp.navigation.CustomRouter
import com.helix.dontkillmyapp.presentation.share.ShareHelper

class VendorDetailsViewModel @ViewModelInject constructor(
    private val router: CustomRouter,
    private val shareHelper: ShareHelper
) : ViewModel() {

    private val manufacturerMutableLiveData = MutableLiveData<Vendor>()
    val vendorLiveData: LiveData<Vendor> = manufacturerMutableLiveData

    fun showManufacturer(vendor: Vendor) {
        manufacturerMutableLiveData.value = vendor
    }

    fun goBack() {
        router.exit()
    }

    fun shareManufacturer() {
        val manufacturer = manufacturerMutableLiveData.requireValue

        shareHelper.shareVendor(manufacturer)
    }

}
