package com.jgcoding.kotlin.commercelistapp.data

import com.jgcoding.kotlin.commercelistapp.data.database.datasource.CommerceLocalDataSource
import com.jgcoding.kotlin.commercelistapp.data.network.CommerceRemoteDataSource
import com.jgcoding.kotlin.commercelistapp.domain.model.Commerce
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class CommerceRepository @Inject constructor(
    private val commerceLocalDataSource: CommerceLocalDataSource,
    private val remoteDataSource: CommerceRemoteDataSource
) {

    companion object {
        private const val TAG = "RepositoryImpl"
    }

    //region API
    val commerces: Flow<List<Commerce>>
        get() = commerceLocalDataSource.commerces.onEach { localCommerces ->
            if(localCommerces.isEmpty()) {
                val remoteCommerces = remoteDataSource.fetchCommerces()
                commerceLocalDataSource.save(remoteCommerces)
            }
        }

    fun findCommerceById(id: Int): Flow<Commerce> = commerceLocalDataSource.findCommerceById(id)
        .filterNotNull()
}

