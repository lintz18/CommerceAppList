package com.jgcoding.kotlin.commercelistapp.domain

import com.jgcoding.kotlin.commercelistapp.domain.model.Commerce
import kotlinx.coroutines.flow.Flow

interface Repository {
    //API
    suspend fun getCommercesApi(): List<Commerce>

    //DATABASE
    suspend fun insertAllCommercesInDataBase(commerces: List<Commerce>)
    fun getCommerceById(id: Int): Flow<Commerce>
    fun getCommercesDatabase(): Flow<List<Commerce>>
    suspend fun deleteAllCommercesDatabase()
}