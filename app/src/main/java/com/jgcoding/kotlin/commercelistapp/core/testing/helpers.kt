package com.jgcoding.kotlin.commercelistapp.core.testing

import com.jgcoding.kotlin.commercelistapp.domain.model.Commerce

fun sampleCommerce(id: Int) =
    Commerce(
        name = "Title $id",
        photo = "url $id",
        cashback = 10.0,
        address = "Address $id",
        openingHours = "Opening Hours $id",
        category = "Category $id",
        location = 0.0 to 0.0,
        id = id,
        distance = 0
    )

fun sampleCommerces(vararg ids: Int) = ids.map { sampleCommerce(it) }