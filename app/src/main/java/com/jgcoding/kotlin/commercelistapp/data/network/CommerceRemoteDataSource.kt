package com.jgcoding.kotlin.commercelistapp.data.network

import com.jgcoding.kotlin.commercelistapp.domain.model.Commerce

interface CommerceRemoteDataSource {
    suspend fun fetchCommerces(): List<Commerce>
}