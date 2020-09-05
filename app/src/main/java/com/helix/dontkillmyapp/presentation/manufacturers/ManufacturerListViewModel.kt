package com.helix.dontkillmyapp.presentation.manufacturers

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helix.dontkillmyapp.data.datasource.ManufacturersSearchableDataSource
import com.helix.dontkillmyapp.data.model.Manufacturer
import com.helix.dontkillmyapp.data.usecase.GetManufacturersUseCase
import com.helix.dontkillmyapp.navigation.CustomRouter
import com.helix.dontkillmyapp.presentation.manufacturer.ManufacturerScreen
import com.helix.dontkillmyapp.presentation.theme.Theme
import com.helix.dontkillmyapp.presentation.theme.ThemeHelper
import com.helix.dontkillmyapp.utils.Finished
import com.helix.dontkillmyapp.utils.LongOperation
import com.helix.dontkillmyapp.utils.OpState
import com.helix.dontkillmyapp.utils.SearchQuery
import com.helix.dontkillmyapp.utils.Success
import com.helix.dontkillmyapp.utils.progressive
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ManufacturerListViewModel @ViewModelInject constructor(
    private val getManufacturersUseCase: GetManufacturersUseCase,
    private val manufacturersSearchableDataSource: ManufacturersSearchableDataSource,
    private val router: CustomRouter,
    private val themeHelper: ThemeHelper
) : ViewModel() {

    private val manufacturersListMutableLiveData = manufacturersSearchableDataSource.manufacturers
    val manufacturerListLiveData : LiveData<List<ManufacturerWrapper>> = manufacturersListMutableLiveData

    private val manufacturersUseCaseState = MutableLiveData<OpState>()
    val manufacturersUseCaseStateLiveData : LiveData<OpState> = manufacturersUseCaseState

    fun getManufacturers() {
        viewModelScope.launch {
            progressive {
                getManufacturersUseCase.execute()
            }.collect {
                manufacturersUseCaseState.postValue(it.state)
                it.doOnSuccess { manufacturers ->
                    manufacturersSearchableDataSource.setBaseManufacturers(manufacturers)
                }
            }
        }
    }

    fun openManufacturer(manufacturer: Manufacturer) {
        router.showAtop(ManufacturerScreen(manufacturer))
    }

    fun filter(searchQuery: SearchQuery) {
        manufacturersSearchableDataSource.onSearchQueryChange(searchQuery)
    }

    fun changeTheme(theme: Theme) {
        themeHelper.changeTheme(theme)
    }
}