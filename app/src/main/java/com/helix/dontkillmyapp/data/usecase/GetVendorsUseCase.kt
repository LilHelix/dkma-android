package com.helix.dontkillmyapp.data.usecase

import com.helix.dontkillmyapp.data.model.Vendor
import com.helix.dontkillmyapp.data.repository.VendorRepository
import com.helix.dontkillmyapp.utils.Try
import com.helix.dontkillmyapp.utils.attempt
import javax.inject.Inject

class GetVendorsUseCase @Inject constructor(
    private val vendorsRepository: VendorRepository
) : UseCase<List<Vendor>> {

    override suspend fun execute(): Try<List<Vendor>> {
        return attempt {
            vendorsRepository.getVendors().vendors.sortedBy { it.sortOrder }
        }
    }
}

private val Vendor.sortOrder : Int
    get() = VendorsSortingRule(this).sortOrder

private class VendorsSortingRule(val vendor: Vendor) {
    // связано с тем, что у некоторых производителей приходит Position == 0, поскольку место не определено.
    val sortOrder: Int
        get() = if (vendor.position == 0) Int.MAX_VALUE else vendor.position
}