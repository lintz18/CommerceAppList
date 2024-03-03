package com.jgcoding.kotlin.commercelistapp.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jgcoding.kotlin.commercelistapp.domain.model.Commerce
import com.jgcoding.kotlin.commercelistapp.domain.usecase.GetCommercesUseCase
import com.jgcoding.kotlin.commercelistapp.domain.usecase.SaveCommerceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCommercesUseCase: GetCommercesUseCase,
    private val saveCommerceUseCase: SaveCommerceUseCase
) :
    ViewModel() {

    private var _uiState = MutableStateFlow<MainViewState>(MainViewState.Loading)
    val uiState: StateFlow<MainViewState> = _uiState

    init {
        viewModelScope.launch {
            _uiState.value = MainViewState.Loading
            val result = withContext(Dispatchers.IO) {
                getCommercesUseCase()
            }
            if (result.isNullOrEmpty())
                _uiState.value = MainViewState.Error("No hay comercios")
            else {
                _uiState.value = MainViewState.Success(result)
                saveCommerceUseCase(result)
            }
        }
    }

}

sealed class MainViewState {
    data object Loading : MainViewState()
    data class Success(val commerces: List<Commerce>) : MainViewState()
    data class Error(val error: String) : MainViewState()
}