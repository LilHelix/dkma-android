package com.helix.dontkillmyapp.presentation.vendors

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object VendorListScreen : SupportAppScreen() {

    override fun getFragment(): Fragment = VendorListFragment()
}