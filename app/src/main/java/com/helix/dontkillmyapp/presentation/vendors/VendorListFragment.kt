package com.helix.dontkillmyapp.presentation.vendors

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.helix.dontkillmyapp.R
import com.helix.dontkillmyapp.extensions.observe
import com.helix.dontkillmyapp.presentation.theme.Theme
import com.helix.dontkillmyapp.utils.OpState
import com.helix.dontkillmyapp.utils.setup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_vendor_list.recyclerViewManufacturerList
import kotlinx.android.synthetic.main.fragment_vendor_list.swipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_vendor_list.toolbar
import kotlinx.android.synthetic.main.fragment_vendor_list.view.toolbar
import kotlinx.android.synthetic.main.fragment_vendor_list.viewError
import javax.inject.Inject

@AndroidEntryPoint
class VendorListFragment : Fragment(R.layout.fragment_vendor_list) {

    @Inject lateinit var vendorListAdapter: VendorListAdapter

    private val vendorListViewModel : VendorListViewModel by viewModels()

    private var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vendorListViewModel.getManufacturers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewManufacturerList.adapter = vendorListAdapter

        toolbar.inflateMenu(R.menu.menu_theme)
        toolbar.setupThemeChanger {
            vendorListViewModel.changeTheme(it)
        }

        vendorListAdapter.onClickListener = { adapterPosition, manufWrapper ->
            vendorListViewModel.openManufacturer(manufWrapper.vendor)
        }
        swipeRefreshLayout.setOnRefreshListener {
            vendorListViewModel.getManufacturers()
        }

        observe(vendorListViewModel.vendorListLiveData) {
            vendorListAdapter.submitList(it)
        }

        observe(vendorListViewModel.getVendorsUseCaseStateLiveData) {
            when (it) {
                OpState.LOADING -> {
                    swipeRefreshLayout.isRefreshing = true
                    viewError.isGone = true
                }
                OpState.FAILURE -> {
                    swipeRefreshLayout.isRefreshing = false
                    viewError.isVisible = true
                }
                OpState.SUCCESS -> {
                    swipeRefreshLayout.isRefreshing = false
                    inflateToolbarWithSearch()
                }
            }
        }
    }

    override fun onDestroyView() {
        searchView = null
        super.onDestroyView()
    }

    private fun inflateToolbarWithSearch() {
        if (toolbar.menu.findItem(R.id.action_search) != null) {
            return
        }

        toolbar.inflateMenu(R.menu.menu_search)
        val newSearchView = (toolbar.menu.findItem(R.id.action_search).actionView as SearchView)
        newSearchView.setup {
            vendorListViewModel.filter(it)
        }
        searchView = newSearchView
    }
}

private fun Toolbar.setupThemeChanger(onThemeChosen: (Theme) -> Unit) {
    toolbar.setOnMenuItemClickListener {
        when (it.itemId) {
            R.id.dayTheme -> onThemeChosen.invoke(Theme.DAY)
            R.id.nightTheme -> onThemeChosen.invoke(Theme.NIGHT)
            R.id.defaultTheme -> onThemeChosen.invoke(Theme.DEFAULT)
        }
        true
    }
}