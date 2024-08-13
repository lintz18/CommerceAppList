package com.jgcoding.kotlin.commercelistapp.ui.main.viewmodel

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jgcoding.kotlin.commercelistapp.domain.model.Commerce
import com.jgcoding.kotlin.commercelistapp.domain.usecase.*
import com.jgcoding.kotlin.commercelistapp.ui.common.Result
import com.jgcoding.kotlin.commercelistapp.ui.common.stateAsResultIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCommercesUseCase: GetCommercesUseCase
) :
    ViewModel() {

    private var coordinates: Location = Location("MyLocation")

    private val uiReady = MutableStateFlow(false)

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<Result<List<Commerce>>> = uiReady
        .filter { it }
        .flatMapLatest {
            getCommercesUseCase().onEach {
                it.map { commerce ->
                    commerce.setDistance(coordinates)
                }
            }
        }
        .stateAsResultIn(viewModelScope)

    fun onUiReady() {
        uiReady.value = true
    }

}
