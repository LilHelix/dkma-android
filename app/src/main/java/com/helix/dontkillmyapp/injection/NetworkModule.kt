package com.helix.dontkillmyapp.injection

import android.app.Application
import com.helix.dontkillmyapp.data.remote.ApiService
import com.helix.dontkillmyapp.data.remote.ApiServiceFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApiService(application: Application) : ApiService = ApiServiceFactory.newInstance(application)

}