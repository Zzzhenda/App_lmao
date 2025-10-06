package com.example.app_lmao.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_lmao.model.Producto
import com.example.app_lmao.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


data class ProductoUIState(
    val isLoading: Boolean = false,
    val productos: List<Producto> = emptyList(),
    val error: String? = null
)

class ViewModelProducto : ViewModel() {

    private val repository = Repository()

    private val _uiState = MutableStateFlow(ProductoUIState())

    val uiState: StateFlow<ProductoUIState> = _uiState.asStateFlow()

    init {
        cargarProductos()
    }

    fun cargarProductos() {

        _uiState.value = ProductoUIState(isLoading = true)


        viewModelScope.launch {
            try {

                val productos = repository.obtenerProductos()


                _uiState.value = ProductoUIState(
                    isLoading = false,
                    productos = productos
                )
            } catch (e: Exception) {

                _uiState.value = ProductoUIState(
                    isLoading = false,
                    error = "Error: ${e.message}"
                )
            }
        }
    }


    fun buscarProductos(query: String) {

        _uiState.value = ProductoUIState(isLoading = true)


        viewModelScope.launch {
            try {
                val productos = repository.buscarProductos(query)

                _uiState.value = ProductoUIState(
                    isLoading = false,
                    productos = productos
                )
            } catch (e: Exception) {
                _uiState.value = ProductoUIState(
                    isLoading = false,
                    error = "Error: ${e.message}"
                )
            }
        }
    }


    fun agregarProducto(producto: Producto) {
        viewModelScope.launch {
            try {
                val productos = repository.agregarProducto(producto)


                _uiState.value = ProductoUIState(
                    isLoading = false,
                    productos = productos
                )
            } catch (e: Exception) {
                _uiState.value = ProductoUIState(
                    isLoading = false,
                    error = "Error: ${e.message}"
                )
            }
        }
    }


    fun eliminarProducto(id: Int) {
        viewModelScope.launch {
            try {
                val productos = repository.eliminarProducto(id)


                _uiState.value = ProductoUIState(
                    isLoading = false,
                    productos = productos
                )
            } catch (e: Exception) {
                _uiState.value = ProductoUIState(
                    isLoading = false,
                    error = "Error: ${e.message}"
                )
            }
        }
    }


    fun actualizarProducto(id: Int, updatedProduct: Producto) {
        viewModelScope.launch {
            try {
                val productos = repository.actualizarProducto(id, updatedProduct)


                _uiState.value = ProductoUIState(
                    isLoading = false,
                    productos = productos
                )
            } catch (e: Exception) {
                _uiState.value = ProductoUIState(
                    isLoading = false,
                    error = "Error: ${e.message}"
                )
            }
        }
    }


    fun obtenerPorCategoria(categoria: String) {
        // Set loading state before fetching category-based products
        _uiState.value = ProductoUIState(isLoading = true)


        viewModelScope.launch {
            try {
                val productos = repository.obtenerPorCategoria(categoria)


                _uiState.value = ProductoUIState(
                    isLoading = false,
                    productos = productos
                )
            } catch (e: Exception) {
                _uiState.value = ProductoUIState(
                    isLoading = false,
                    error = "Error: ${e.message}"
                )
            }
        }
    }
}
