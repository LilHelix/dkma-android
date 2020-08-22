package com.helix.dontkillmyapp.presentation.manufacturers

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.helix.dontkillmyapp.R
import com.helix.dontkillmyapp.extensions.observe
import com.helix.dontkillmyapp.extensions.toast
import com.helix.dontkillmyapp.presentation.theme.Theme
import com.helix.dontkillmyapp.presentation.theme.ThemeHelper
import com.helix.dontkillmyapp.utils.setup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_manufacturer_list.recyclerViewManufacturerList
import kotlinx.android.synthetic.main.fragment_manufacturer_list.swipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_manufacturer_list.toolbar
import kotlinx.android.synthetic.main.fragment_manufacturer_list.view.toolbar
import kotlinx.android.synthetic.main.fragment_manufacturer_list.viewError
import javax.inject.Inject

@AndroidEntryPoint
class ManufacturerListFragment : Fragment(R.layout.fragment_manufacturer_list) {

    @Inject lateinit var manufacturerListAdapter: ManufacturerListAdapter

    private val manufacturerListViewModel : ManufacturerListViewModel by viewModels()

    private var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        manufacturerListViewModel.getManufacturers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewManufacturerList.adapter = manufacturerListAdapter

        toolbar.inflateMenu(R.menu.menu_theme)
        toolbar.setupThemeChanger {
            manufacturerListViewModel.changeTheme(it)
        }

        manufacturerListAdapter.onClickListener = { adapterPosition, manufacturer ->
            manufacturerListViewModel.openManufacturer(manufacturer)
        }
        swipeRefreshLayout.setOnRefreshListener {
            manufacturerListViewModel.getManufacturers()
        }

        observe(manufacturerListViewModel.manufacturerListLiveData) {
            it.fold(
                onStarted = {
                    swipeRefreshLayout.isRefreshing = true
                    viewError.isGone = true
                },
                onSuccess = { manufacturers ->
                    swipeRefreshLayout.isRefreshing = false
                    manufacturerListAdapter.submitList(manufacturers)
                    inflateToolbarWithSearch()
                },
                onError = {
                    swipeRefreshLayout.isRefreshing = false
                    viewError.isVisible = true
                }
            )
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
            manufacturerListViewModel.filter(it)
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