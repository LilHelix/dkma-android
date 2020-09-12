package com.helix.dontkillmyapp.presentation.vendordetails

import android.content.res.Resources
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.helix.dontkillmyapp.R
import com.helix.dontkillmyapp.data.model.Vendor
import com.helix.dontkillmyapp.extensions.observe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_vendor_details.textViewManufacturerDevSolution
import kotlinx.android.synthetic.main.fragment_vendor_details.textViewManufacturerInfo
import kotlinx.android.synthetic.main.fragment_vendor_details.textViewManufacturerUserSolution
import kotlinx.android.synthetic.main.fragment_vendor_details.toolbar
import javax.inject.Inject

@AndroidEntryPoint
class VendorDetailsFragment : Fragment(R.layout.fragment_vendor_details) {

    @Inject lateinit var imageGetter: Html.ImageGetter

    private val vendorDetailsViewModel: VendorDetailsViewModel by viewModels()

    private val vendor: Vendor by lazy {
        requireArguments().getParcelable(VendorDetailsScreen.KEY_MANUFACTURER)
            ?: throw IllegalArgumentException("You need this argument to launch this screen")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vendorDetailsViewModel.showManufacturer(vendor)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.setNavigationOnClickListener {
            vendorDetailsViewModel.goBack()
        }
        toolbar.inflateMenu(R.menu.menu_vendor_share)
        toolbar.menu.findItem(R.id.action_share).setOnMenuItemClickListener {
            vendorDetailsViewModel.shareManufacturer()
            true
        }

        observe(vendorDetailsViewModel.vendorLiveData) {
            toolbar.title = it.name
            toolbar.subtitle = resources.getPlaceString(it.position)

            textViewManufacturerInfo.text =
                HtmlCompat.fromHtml(it.explanation, HtmlCompat.FROM_HTML_MODE_LEGACY, imageGetter, null)
            textViewManufacturerUserSolution.text =
                HtmlCompat.fromHtml(it.userSolution, HtmlCompat.FROM_HTML_MODE_LEGACY, imageGetter, null)
            textViewManufacturerDevSolution.text = if (it.developerSolution.any { it.isLetterOrDigit() }) {
                HtmlCompat.fromHtml(it.developerSolution, HtmlCompat.FROM_HTML_MODE_LEGACY, imageGetter, null)
            } else {
                resources.getString(R.string.no_solution_found)
            }
        }
    }
}

private const val NO_PLACE_DETERMINED = 0
private const val FIRST_PLACE = 1
private const val SECOND_PLACE = 2
private const val THIRD_PLACE = 3
private const val OTHER_DEVICES_POSITION = 9999

private fun Resources.getPlaceString(position: Int) : String = when (position) {
    NO_PLACE_DETERMINED -> getString(R.string.no_place_determined)
    FIRST_PLACE -> getString(R.string.first_in_our_parade)
    SECOND_PLACE -> getString(R.string.second_in_our_parade)
    THIRD_PLACE -> getString(R.string.third_in_our_parade)
    OTHER_DEVICES_POSITION -> getString(R.string.all_other_devices)
    else -> getString(R.string.certain_place_in_our_parade, position)
}