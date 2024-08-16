package com.jgcoding.kotlin.commercelistapp.domain.usecase

import com.jgcoding.kotlin.commercelistapp.data.CommerceRepository
import com.jgcoding.kotlin.commercelistapp.data.database.datasource.CommerceLocalDataSource
import javax.inject.Inject

class GetCommerceIdUseCase @Inject constructor(
    private val repository: CommerceRepository
) {
    operator fun invoke(id: Int) = repository.findCommerceById(id)
}