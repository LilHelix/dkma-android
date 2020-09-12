package com.helix.dontkillmyapp.presentation.share

import android.content.Context
import android.content.Intent
import com.helix.dontkillmyapp.R
import com.helix.dontkillmyapp.data.model.Vendor
import com.helix.dontkillmyapp.data.remote.ENDPOINT
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class ShareHelperImpl @Inject constructor(
    @ActivityContext private val context: Context
) : ShareHelper {

    companion object {
        private const val TEXT_TYPE = "text/plain"
    }

    private val shareIntentTag = context.getString(R.string.action_share)

    override fun shareVendor(vendor: Vendor) {
        val message = "$ENDPOINT${vendor.url}"
        Intent(Intent.ACTION_SEND).apply {
            type = TEXT_TYPE
            putExtra(Intent.EXTRA_TEXT, message)
            context.startActivity(Intent.createChooser(this, shareIntentTag))
        }
    }
}