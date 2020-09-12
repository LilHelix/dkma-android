package com.helix.dontkillmyapp.injection

import com.helix.dontkillmyapp.data.holders.VendorsDataHolder
import com.helix.dontkillmyapp.data.holders.VendorsDataHolderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.InternalCoroutinesApi

@Module
@InstallIn(ApplicationComponent::class)
object DataSourceModule {

    @InternalCoroutinesApi
    @Provides
    fun provideDataSourceModule() : VendorsDataHolder = VendorsDataHolderImpl()
}