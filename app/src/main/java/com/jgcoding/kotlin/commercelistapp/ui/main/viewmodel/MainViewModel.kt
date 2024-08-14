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

    var categoriesList: MutableList<String> = mutableListOf()

    private val _filteredCommerces = MutableStateFlow<List<Commerce>>(emptyList())
    val filteredCommerces: StateFlow<List<Commerce>> = _filteredCommerces

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<Result<List<Commerce>>> = uiReady
        .filter { it }
        .flatMapLatest {
            getCommercesUseCase().onEach { list ->
                categoriesList = list.map { commerce ->
                    commerce.category
                }.distinct().toMutableList()

                categoriesList.add("All")

                categoriesList.sortBy { it.lowercase() }

                list.map { commerce ->
                    commerce.setDistance(coordinates)
                }
                _filteredCommerces.value = list
            }
        }
        .stateAsResultIn(viewModelScope)

    fun onUiReady() {
        uiReady.value = true
    }

    fun onCategoryClick(s: String) {
        val allCommerces = (state.value as? Result.Success)?.data.orEmpty()

        _filteredCommerces.value = if (s.equals("All", ignoreCase = true)) {
            allCommerces
        } else {
            allCommerces.filter { it.category.contains(s, ignoreCase = true) }
        }

//        _filteredCommerces.value = (state.value as? Result.Success)?.data.orEmpty().filter {
//            it.category.contains(s, ignoreCase = true)
//        }
    }

}
