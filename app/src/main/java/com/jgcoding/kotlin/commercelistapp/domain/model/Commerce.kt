package com.jgcoding.kotlin.commercelistapp.domain.model

import android.location.Location
import com.jgcoding.kotlin.commercelistapp.data.database.entity.CommerceEntity

data class Commerce(
    val name: String,
    val photo: String,
    val cashback: Double,
    val address: String,
    val openingHours: String,
    val category: String,
    val location: Pair<Double, Double>,
    val id: Int,
    var distance: Int = 0
) {

    fun setDistance(coordinates: Location) {
        var commerceLocation = Location("CommerceLocation")
        commerceLocation.latitude = location.second
        commerceLocation.longitude = location.first
        this.distance = coordinates.distanceTo(commerceLocation).toInt()
    }

    fun checkDistance(): Boolean = distance < 1000

    fun toEntity() = CommerceEntity(
        name = name,
        photo = photo,
        cashback = cashback,
        address = address,
        openingHours = openingHours,
        category = category,
        location = com.jgcoding.kotlin.commercelistapp.data.database.entity.Location(location.first, location.second),
        id = id
    )

}

//private fun List<Commerce>.toEntity(): List<CommerceEntity> {
//
//}