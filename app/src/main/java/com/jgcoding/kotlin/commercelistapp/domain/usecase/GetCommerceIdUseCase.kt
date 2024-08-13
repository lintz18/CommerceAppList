package com.jgcoding.kotlin.commercelistapp.domain.usecase

import com.jgcoding.kotlin.commercelistapp.data.database.datasource.CommerceLocalDataSource
import javax.inject.Inject

class GetCommerceIdUseCase @Inject constructor(private val localDataSource: CommerceLocalDataSource) {
    operator fun invoke(id: Int) = localDataSource.findCommerceById(id)
}