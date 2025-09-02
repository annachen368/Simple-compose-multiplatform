package io.github.annachen368.simple_compose_multiplatform.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.annachen368.simple_compose_multiplatform.data.model.Cat
import io.github.annachen368.simple_compose_multiplatform.data.repository.CatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CatViewModel(val catRepository: CatRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<CatUiState> = MutableStateFlow(CatUiState.Loading)
    val uiState: StateFlow<CatUiState> = _uiState

    init {
        catRepository.getCats()
            .onEach { cats ->
                if (_uiState.value !is CatUiState.Error) {
                    if (cats.isEmpty()) {
                        _uiState.update { CatUiState.Empty }
                    } else {
                        _uiState.update { CatUiState.Success(cats) }
                    }
                }
            }
            .launchIn(viewModelScope)

        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.update { CatUiState.Loading }

            try {
                catRepository.refresh()
            } catch (e: Exception) {
                _uiState.update { CatUiState.Error(e.message ?: "Error") }
            }
        }
    }
}

sealed interface CatUiState {
    data object Loading : CatUiState
    data object Empty : CatUiState
    data class Success(val list: List<Cat>) : CatUiState
    data class Error(val message: String) : CatUiState
}