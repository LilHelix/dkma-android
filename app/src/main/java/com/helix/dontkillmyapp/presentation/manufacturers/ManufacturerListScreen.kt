package com.helix.dontkillmyapp.presentation.manufacturers

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object ManufacturerListScreen : SupportAppScreen() {

    override fun getFragment(): Fragment = ManufacturerListFragment()
}