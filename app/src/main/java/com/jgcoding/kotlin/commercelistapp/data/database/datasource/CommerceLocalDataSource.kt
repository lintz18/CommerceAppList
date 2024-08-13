package com.jgcoding.kotlin.commercelistapp.data.database.datasource

import com.jgcoding.kotlin.commercelistapp.domain.model.Commerce
import kotlinx.coroutines.flow.Flow

interface CommerceLocalDataSource {
    val commerces: Flow<List<Commerce>>
    fun findCommerceById(id: Int): Flow<Commerce?>
    suspend fun save(commerces: List<Commerce>)
}