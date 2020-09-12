package com.helix.dontkillmyapp.presentation.vendordetails

import androidx.transition.Transition
import android.view.Gravity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.transition.Fade
import androidx.transition.Slide
import androidx.transition.TransitionSet
import com.helix.dontkillmyapp.data.model.Vendor
import ru.terrakok.cicerone.android.support.SupportAppScreen

data class VendorDetailsScreen(
    val vendor: Vendor,
    val openTransition: Transition = ManufacturerTransition(TransitionMode.OPEN),
    val closeTransition: Transition = ManufacturerTransition(TransitionMode.CLOSE)
) : SupportAppScreen() {

    companion object {
        const val KEY_MANUFACTURER = "key_manufacturer"
    }

    override fun getFragment(): Fragment? {
        return VendorDetailsFragment().apply {
            arguments = bundleOf(
                KEY_MANUFACTURER to vendor
            )
            enterTransition = openTransition
            exitTransition = closeTransition
        }
    }
}

enum class TransitionMode {
    OPEN, CLOSE
}

class ManufacturerTransition(transitionMode: TransitionMode) : TransitionSet() {
    init {
        addTransition(Slide(Gravity.BOTTOM).setDuration(250L))
        val fadeInMode = if (transitionMode == TransitionMode.OPEN) Fade.MODE_IN else Fade.MODE_OUT
        addTransition(Fade(fadeInMode).setDuration(100L))
        ordering = ORDERING_TOGETHER
    }
}