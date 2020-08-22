package com.helix.dontkillmyapp.presentation.manufacturer

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.helix.dontkillmyapp.data.model.Manufacturer
import com.helix.dontkillmyapp.navigation.CustomRouter
import ru.terrakok.cicerone.Router

class ManufacturerViewModel @ViewModelInject constructor(
    private val router: CustomRouter
) : ViewModel() {

    private val manufacturerMutableLiveData = MutableLiveData<Manufacturer>()
    val manufacturerLiveData: LiveData<Manufacturer> = manufacturerMutableLiveData

    fun showManufacturer(manufacturer: Manufacturer) {
        manufacturerMutableLiveData.value = manufacturer
    }

    fun goBack() {
        router.exit()
    }

}
