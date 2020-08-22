package com.helix.dontkillmyapp.data.usecase

import com.helix.dontkillmyapp.data.model.Manufacturer
import com.helix.dontkillmyapp.data.repository.ManufacturerRepository
import com.helix.dontkillmyapp.utils.Try
import com.helix.dontkillmyapp.utils.attempt
import javax.inject.Inject

class GetManufacturersUseCase @Inject constructor(
    private val manufacturerRepository: ManufacturerRepository
) : UseCase<List<Manufacturer>> {

    override suspend fun execute(): Try<List<Manufacturer>> {
        return attempt {
            manufacturerRepository.getManufacturers().vendors.sortedBy { it.sortOrder }
        }
    }
}

private val Manufacturer.sortOrder : Int
    get() = ManufacturersCaseSortingRule(this).sortOrder

private class ManufacturersCaseSortingRule(val manufacturer: Manufacturer) {
    // связано с тем, что у некоторых производителей приходит Position == 0, поскольку место не определено.
    val sortOrder: Int
        get() = if (manufacturer.position == 0) Int.MAX_VALUE else manufacturer.position
}