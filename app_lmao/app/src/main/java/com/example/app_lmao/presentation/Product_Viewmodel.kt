package com.example.backend_test.Presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.backend_test.Data.Model.Product
import com.example.backend_test.Data.Repository.Product_Repository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class Product_Viewmodel : ViewModel() {

    // Repository (fuente de datos)
    private val repository = Product_Repository()

    // Estado PRIVADO (mutable, solo el ViewModel puede modificarlo)
    private val _uiState = MutableStateFlow(ProductoUIState())

    // Estado PÚBLICO (inmutable, la View solo puede observarlo)
    val uiState: StateFlow<ProductoUIState> = _uiState.asStateFlow()

    // Al crear el ViewModel, carga los productos automáticamente
    init {
        cargarProductos()
    }

    /**
     * Carga todos los productos desde el repositorio
     */
    fun cargarProductos() {
        // viewModelScope: coroutine que se cancela automáticamente
        // cuando el ViewModel es destruido
        viewModelScope.launch {
            // Actualiza el estado: está cargando
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            try {
                // Obtiene productos del repositorio (asíncrono)
                val productos = repository.obtener_productos()

                // Actualiza el estado: productos cargados exitosamente
                _uiState.value = _uiState.value.copy(
                    productos = productos,
                    isLoading = false
                )
            } catch (e: Exception) {
                // Si hay error, actualiza el estado con el mensaje
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Error al cargar productos: ${e.message}"
                )
            }
        }
    }

    /**
     * Busca productos por texto
     */
    fun buscarProductos(query: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                val productos = if (query.isBlank()) {
                    repository.obtener_productos()
                } else {
                    repository.buscar_productos(query)
                }

                _uiState.value = _uiState.value.copy(
                    productos = productos,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Error en la búsqueda"
                )
            }
        }
    }
}
/**
 * Estado de la UI
 * Representa todo lo que la interfaz necesita mostrar
 */
data class ProductoUIState(
    val productos: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)