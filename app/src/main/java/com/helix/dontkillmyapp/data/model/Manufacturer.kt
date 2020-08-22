package com.helix.dontkillmyapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Manufacturer(
    val name: String,
    val manufacturer: List<String>,
    val url: String,
    val award: Int,
    val position: Int,
    val explanation: String,
    @SerializedName("user_solution") val userSolution: String,
    @SerializedName("developer_solution") val developerSolution: String
) : Parcelable {

}