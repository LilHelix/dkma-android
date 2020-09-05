package com.helix.dontkillmyapp.presentation.manufacturers

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.helix.dontkillmyapp.R
import com.helix.dontkillmyapp.data.model.Manufacturer
import com.helix.dontkillmyapp.extensions.inflateFromThis
import com.helix.dontkillmyapp.utils.DeviceManufacturerSimilarityChecker
import kotlinx.android.synthetic.main.item_manufacturer_list.view.textViewManufacturer
import javax.inject.Inject

class ManufacturerListAdapter @Inject constructor(

) : ListAdapter<ManufacturerWrapper, ManufacturerViewHolder>(
    DeviceManufacturerSimilarityChecker.provideItemCallback()
) {

    var onClickListener : (adapterPosition: Int, ManufacturerWrapper) -> Unit = {_, _ -> }

    private val localOnClickListener : (adapterPosition: Int) -> Unit = {
        val item = getItem(it)
        onClickListener.invoke(it, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManufacturerViewHolder {
        return ManufacturerViewHolder(parent, localOnClickListener)
    }

    override fun onBindViewHolder(holder: ManufacturerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ManufacturerViewHolder(
    parent: ViewGroup,
    localOnClickListener: (adapterPosition: Int) -> Unit
) : RecyclerView.ViewHolder(parent.inflateFromThis(R.layout.item_manufacturer_list)) {

    init {
        itemView.textViewManufacturer.setOnClickListener {
            localOnClickListener.invoke(bindingAdapterPosition)
        }
    }

    fun bind(item: ManufacturerWrapper) {
        itemView.textViewManufacturer.text = item.manufacturer.name
    }
}