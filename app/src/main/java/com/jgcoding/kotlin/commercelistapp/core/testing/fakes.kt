package com.jgcoding.kotlin.commercelistapp.core.testing

import com.jgcoding.kotlin.commercelistapp.data.CommerceRepository
import com.jgcoding.kotlin.commercelistapp.data.database.datasource.CommerceLocalDataSource
import com.jgcoding.kotlin.commercelistapp.data.network.CommerceRemoteDataSource
import com.jgcoding.kotlin.commercelistapp.domain.model.Commerce
import kotlinx.coroutines.flow.*

fun buildCommerceRepositoryWith(
    localData: List<Commerce> = emptyList(),
    remoteData: List<Commerce> = emptyList()
): CommerceRepository {
    val localDataSource = FakeLocalDataSource().apply { inMemoryCommerces.value = localData }
    val remoteDataSource = FakeRemoteDataSource().apply { commerces = remoteData }
    return CommerceRepository(localDataSource, remoteDataSource)
}

class FakeLocalDataSource : CommerceLocalDataSource {

    val inMemoryCommerces = MutableStateFlow<List<Commerce>>(emptyList())

    override val commerces = inMemoryCommerces

    override fun findCommerceById(id: Int): Flow<Commerce?> =
        inMemoryCommerces.map { it.firstOrNull { movie -> movie.id == id } }


    override suspend fun save(commerces: List<Commerce>) {
        inMemoryCommerces.value = commerces
    }
}

class FakeRemoteDataSource : CommerceRemoteDataSource {

    var commerces = sampleCommerces(1, 2, 3, 4)
    override suspend fun fetchCommerces(): List<Commerce> = commerces
}