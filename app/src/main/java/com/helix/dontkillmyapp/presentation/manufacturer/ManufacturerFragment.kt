package com.helix.dontkillmyapp.presentation.manufacturer

import android.content.res.Resources
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.helix.dontkillmyapp.R
import com.helix.dontkillmyapp.data.model.Manufacturer
import com.helix.dontkillmyapp.extensions.observe
import com.helix.dontkillmyapp.presentation.manufacturers.ManufacturerListAdapter
import com.helix.dontkillmyapp.presentation.manufacturers.ManufacturerListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_manufacturer.textViewManufacturerDevSolution
import kotlinx.android.synthetic.main.fragment_manufacturer.textViewManufacturerInfo
import kotlinx.android.synthetic.main.fragment_manufacturer.textViewManufacturerUserSolution
import kotlinx.android.synthetic.main.fragment_manufacturer.toolbar
import kotlinx.android.synthetic.main.fragment_manufacturer_list.recyclerViewManufacturerList
import kotlinx.android.synthetic.main.fragment_manufacturer_list.swipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_manufacturer_list.viewError
import javax.inject.Inject

@AndroidEntryPoint
class ManufacturerFragment : Fragment(R.layout.fragment_manufacturer) {

    @Inject lateinit var imageGetter: Html.ImageGetter

    private val manufacturerViewModel: ManufacturerViewModel by viewModels()

    private val manufacturer: Manufacturer
        get() = requireArguments().getParcelable(ManufacturerScreen.KEY_MANUFACTURER)
            ?: throw IllegalArgumentException("You need this argument to launch this screen")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        manufacturerViewModel.showManufacturer(manufacturer)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.setNavigationOnClickListener {
            manufacturerViewModel.goBack()
        }

        observe(manufacturerViewModel.manufacturerLiveData) {
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