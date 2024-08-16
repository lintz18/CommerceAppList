package com.jgcoding.kotlin.commercelistapp.di

import androidx.lifecycle.SavedStateHandle
import com.jgcoding.kotlin.commercelistapp.data.database.datasource.CommerceRoomDataSourceCommerce
import com.jgcoding.kotlin.commercelistapp.data.database.datasource.CommerceLocalDataSource
import com.jgcoding.kotlin.commercelistapp.data.network.CommerceRemoteDataSource
import com.jgcoding.kotlin.commercelistapp.data.network.CommerceServerDataSource
import com.jgcoding.kotlin.commercelistapp.ui.common.NavArgs
import dagger.*
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class FrameworkCommerceModule {

    @Binds
    abstract fun bindLocalDataSource(localDataSource: CommerceRoomDataSourceCommerce): CommerceLocalDataSource

    @Binds
    abstract fun bindRemoteDataSource(remoteDataSource: CommerceServerDataSource): CommerceRemoteDataSource
}