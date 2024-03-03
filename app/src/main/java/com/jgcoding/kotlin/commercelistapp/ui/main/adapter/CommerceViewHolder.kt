package com.jgcoding.kotlin.commercelistapp.ui.main.adapter

import android.content.Context
import android.graphics.Color
import android.location.Location
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jgcoding.kotlin.commercelistapp.R
import com.jgcoding.kotlin.commercelistapp.databinding.ItemCommerceBinding
import com.jgcoding.kotlin.commercelistapp.domain.model.Commerce

class CommerceViewHolder(
    private val context: Context,
    private val binding: ItemCommerceBinding,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        private const val TAG = "CommerceViewHolder"
        private const val FOOD = "FOOD"
        private const val GAS_STATION = "GAS_STATION"
        private const val SHOPPING = "SHOPPING"
        private const val ELECTRIC_STATION = "ELECTRIC_STATION"
        private const val DIRECT_SALES = "DIRECT_SALES"
        private const val LEISURE = "LEISURE"
        private const val BEAUTY = "BEAUTY"
    }


    fun bind(commerce: Commerce) {
        binding.apply {
            Glide.with(itemView)
                .load(commerce.photo)
                .error(com.google.android.material.R.drawable.mtrl_ic_error)
                .into(ivCommerceImage)

            tvCommerceName.text = commerce.name ?: ""
            tvCommerceDescription.text = commerce.address ?: ""
            tvDistance.text = "${commerce.distance}m."

            Log.i(TAG, "Category: ${commerce.category}")
            when (commerce.category) {
                FOOD -> {
                    ivCategory.setImageResource(R.drawable.catering_white)
                    clCategory.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow))
                }
                GAS_STATION -> {
                    ivCategory.setImageResource(R.drawable.ees_white)
                    clCategory.setBackgroundColor(ContextCompat.getColor(context, R.color.orange))
                }
                SHOPPING -> {
                    ivCategory.setImageResource(R.drawable.cart_white)
                    clCategory.setBackgroundColor(ContextCompat.getColor(context, R.color.dark_blue))
                }
                ELECTRIC_STATION -> {
                    ivCategory.setImageResource(R.drawable.electric_scooter_white)
                    clCategory.setBackgroundColor(ContextCompat.getColor(context, R.color.orange))
                }
                DIRECT_SALES -> {
                    ivCategory.setImageResource(R.drawable.truck_white)
                    clCategory.setBackgroundColor(ContextCompat.getColor(context, R.color.purple))
                }
                LEISURE -> {
                    ivCategory.setImageResource(R.drawable.leisure_white)
                    clCategory.setBackgroundColor(ContextCompat.getColor(context, R.color.dark_blue))
                }
                BEAUTY -> {
                    ivCategory.setImageResource(R.drawable.car_wash_white)
                    clCategory.setBackgroundColor(ContextCompat.getColor(context, R.color.gray_text_color))
                }
            }

            cvCommerce.setOnClickListener {
                onItemClick(commerce.id)
            }
        }
    }

}