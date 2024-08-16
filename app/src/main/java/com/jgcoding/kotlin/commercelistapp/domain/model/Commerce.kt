package com.jgcoding.kotlin.commercelistapp.domain.model

import android.location.Location
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.toLowerCase
import com.jgcoding.kotlin.commercelistapp.R
import com.jgcoding.kotlin.commercelistapp.core.systemdesign.*
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
        val commerceLocation = Location("CommerceLocation")
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

    fun getIconCategory(s: String? = null): Int {
        val category = s ?: this.category
        return when (category.lowercase()) {
            "all" -> R.drawable.placeholder
            "beauty" -> R.drawable.cart_colour
            "direct_sales" -> R.drawable.placeholder
            "electric_station" -> R.drawable.electric_scootter_colour
            "food" -> R.drawable.catering_colour
            "gas_station" -> R.drawable.ees_colour
            "leisure" -> R.drawable.car_wash_colour
            "shopping" -> R.drawable.cart_colour
            else -> R.drawable.placeholder
        }
    }

    fun getCategoryColor(s: String? = null): Color {
        val category = s ?: this.category
        return when (category.lowercase()) {
            "all" -> dark_blue
            "beauty" -> purple
            "direct_sales" -> orange_light
            "electric_station" -> yellow
            "food" -> gray_text_color
            "gas_station" -> blue
            "leisure" -> orange
            "shopping" -> gray_text_color
            else -> black
        }
    }

}

//private fun List<Commerce>.toEntity(): List<CommerceEntity> {
//
//}