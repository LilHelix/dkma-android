package com.helix.dontkillmyapp.presentation.vendors

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.helix.dontkillmyapp.R
import com.helix.dontkillmyapp.extensions.inflateFromThis
import com.helix.dontkillmyapp.extensions.setDrawable
import com.helix.dontkillmyapp.utils.VendorSimilarityChecker
import kotlinx.android.synthetic.main.item_vendor.view.textViewManufacturer
import javax.inject.Inject

class VendorListAdapter @Inject constructor(

) : ListAdapter<VendorWrapper, VendorViewHolder>(
    VendorSimilarityChecker.provideItemCallback()
) {

    var onClickListener : (adapterPosition: Int, VendorWrapper) -> Unit = { _, _ -> }

    private val localOnClickListener : (adapterPosition: Int) -> Unit = {
        val item = getItem(it)
        onClickListener.invoke(it, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VendorViewHolder {
        return VendorViewHolder(parent, localOnClickListener)
    }

    override fun onBindViewHolder(holder: VendorViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class VendorViewHolder(
    parent: ViewGroup,
    localOnClickListener: (adapterPosition: Int) -> Unit
) : RecyclerView.ViewHolder(parent.inflateFromThis(R.layout.item_vendor)) {

    init {
        itemView.textViewManufacturer.setOnClickListener {
            localOnClickListener.invoke(bindingAdapterPosition)
        }
    }

    fun bind(item: VendorWrapper) {
        itemView.textViewManufacturer.text = item.vendor.name
        itemView.textViewManufacturer.setDrawable(right = if (item.isViewedAlready) R.drawable.ic_done else 0)
    }
}