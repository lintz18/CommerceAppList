package com.jgcoding.kotlin.commercelistapp.data.database.datasource

import com.jgcoding.kotlin.commercelistapp.data.database.dao.CommerceDao
import com.jgcoding.kotlin.commercelistapp.data.database.entity.CommerceEntity
import com.jgcoding.kotlin.commercelistapp.domain.model.Commerce
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class CommerceRoomDataSourceCommerce @Inject constructor(private val commerceDao: CommerceDao) :
    CommerceLocalDataSource {

    override val commerces: Flow<List<Commerce>> =
        commerceDao.getAllCommerces().map { it.toDomainCommerce() }

    override fun findCommerceById(id: Int): Flow<Commerce?> =
        commerceDao.getCommerceById(id).map { it?.toDomain() }


    override suspend fun save(commerces: List<Commerce>) =
        commerceDao.insertCommerces(commerces.toEntityCommerce())

}

private fun List<CommerceEntity>.toDomainCommerce() = map { it.toDomain() }

private fun List<Commerce>.toEntityCommerce() = map { it.toEntity() }