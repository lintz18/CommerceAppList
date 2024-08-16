package com.jgcoding.kotlin.commercelistapp.di

import androidx.lifecycle.SavedStateHandle
import com.jgcoding.kotlin.commercelistapp.ui.common.NavArgs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class DetailViewModelModule {

    @Provides
    @ViewModelScoped
    @CommerceId
    fun provideMovieId(savedStateHandle: SavedStateHandle): Int {
        return savedStateHandle[NavArgs.CommerceId.key] ?: -1
    }

}