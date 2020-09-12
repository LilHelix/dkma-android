package com.helix.dontkillmyapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.helix.dontkillmyapp.navigation.CustomAppNavigator
import com.helix.dontkillmyapp.navigation.CustomRouter
import com.helix.dontkillmyapp.presentation.vendors.VendorListScreen
import dagger.hilt.android.AndroidEntryPoint
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject

@AndroidEntryPoint
class HostActivity : AppCompatActivity(R.layout.activity_host) {

    @Inject
    lateinit var router: CustomRouter

    @Inject
    lateinit var navHolder: NavigatorHolder

    private val navigator = CustomAppNavigator(this, R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        router.newRootScreen(VendorListScreen)
    }

    override fun onResume() {
        super.onResume()
        navHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navHolder.removeNavigator()
    }
}