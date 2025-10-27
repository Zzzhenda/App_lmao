package com.example.lmao.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lmao.data.model.Pastel
import com.example.lmao.data.repository.PastelRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PastelUIState(
    val pasteles: List<Pastel> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class PastelViewModel : ViewModel() {
    private val repository = PastelRepository()

    private val _uiState = MutableStateFlow(PastelUIState())
    val uiState: StateFlow<PastelUIState> = _uiState.asStateFlow()

    init {
        cargarPasteles()
    }

    fun cargarPasteles() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val pasteles = repository.obtenerPasteles()
                _uiState.value = _uiState.value.copy(pasteles = pasteles, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Error al cargar pasteles"
                )
            }
        }
    }

    fun buscarPasteles(query: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val resultados = if (query.isBlank()) repository.obtenerPasteles()
                else repository.buscarPorNombre(query)
                _uiState.value = _uiState.value.copy(pasteles = resultados, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Error en la búsqueda"
                )
            }
        }
    }

    fun filtrarPorCategoria(categoria: String) {
        viewModelScope.launch {
            val filtrados = repository.obtenerPorCategoria(categoria)
            _uiState.value = _uiState.value.copy(pasteles = filtrados)
        }
    }

    fun agregarPastel(pastel: Pastel) {
        repository.agregarPastel(pastel)
        _uiState.value = _uiState.value.copy(pasteles = repository.obtenerPasteles())
    }
}
