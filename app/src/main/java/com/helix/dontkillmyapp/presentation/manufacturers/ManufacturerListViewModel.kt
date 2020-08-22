package com.helix.dontkillmyapp.presentation.manufacturers

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helix.dontkillmyapp.data.model.Manufacturer
import com.helix.dontkillmyapp.data.usecase.GetManufacturersUseCase
import com.helix.dontkillmyapp.navigation.CustomRouter
import com.helix.dontkillmyapp.presentation.manufacturer.ManufacturerScreen
import com.helix.dontkillmyapp.presentation.theme.Theme
import com.helix.dontkillmyapp.presentation.theme.ThemeHelper
import com.helix.dontkillmyapp.utils.Finished
import com.helix.dontkillmyapp.utils.LongOperation
import com.helix.dontkillmyapp.utils.SearchQuery
import com.helix.dontkillmyapp.utils.Success
import com.helix.dontkillmyapp.utils.progressive
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ManufacturerListViewModel @ViewModelInject constructor(
    private val getManufacturersUseCase: GetManufacturersUseCase,
    private val router: CustomRouter,
    private val themeHelper: ThemeHelper
) : ViewModel() {

    private val manufacturerListMutableLiveData = MutableLiveData<LongOperation<List<Manufacturer>>>()
    val manufacturerListLiveData : LiveData<LongOperation<List<Manufacturer>>> = manufacturerListMutableLiveData

    private val allManufacturersListLiveData = MutableLiveData<List<Manufacturer>>()

    fun getManufacturers() {
        viewModelScope.launch {
            progressive {
                getManufacturersUseCase.execute()
            }.collect {
                manufacturerListMutableLiveData.postValue(it)
                it.doOnSuccess { manufacturers ->
                    allManufacturersListLiveData.postValue(manufacturers)
                }
            }
        }
    }

    fun openManufacturer(manufacturer: Manufacturer) {
        router.showAtop(ManufacturerScreen(manufacturer))
    }

    fun filter(searchQuery: SearchQuery) {
        val allManufacturers = allManufacturersListLiveData.value ?: return

        val manufacturersToShow = if (searchQuery.isEmpty) {
            allManufacturers
        } else {
            allManufacturers.filter { it.name.startsWith(searchQuery.query, ignoreCase = true) }
        }

        manufacturerListMutableLiveData.value = Finished(Success(manufacturersToShow))
    }

    fun changeTheme(theme: Theme) {
        themeHelper.changeTheme(theme)
    }
}