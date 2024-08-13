package com.jgcoding.kotlin.commercelistapp.data.network

import com.jgcoding.kotlin.commercelistapp.domain.model.Commerce
import javax.inject.Inject

internal class CommerceServerDataSource @Inject constructor(private val apiService: ApiService) : CommerceRemoteDataSource {

    override suspend fun fetchCommerces(): List<Commerce> {
        return apiService.getCommerces()
            .map { it.toDomain() }
    }

}
