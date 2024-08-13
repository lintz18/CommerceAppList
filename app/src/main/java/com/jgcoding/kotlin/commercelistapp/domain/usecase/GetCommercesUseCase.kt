package com.jgcoding.kotlin.commercelistapp.domain.usecase

import com.jgcoding.kotlin.commercelistapp.data.CommerceRepository
import com.jgcoding.kotlin.commercelistapp.domain.model.Commerce
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCommercesUseCase @Inject constructor(private val repository: CommerceRepository) {
    operator fun invoke(): Flow<List<Commerce>> = repository.commerces
}