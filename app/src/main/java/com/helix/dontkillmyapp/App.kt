package com.helix.dontkillmyapp

import android.app.Application
import android.util.Log
import com.helix.dontkillmyapp.data.usecase.GetManufacturersUseCase
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App: Application()