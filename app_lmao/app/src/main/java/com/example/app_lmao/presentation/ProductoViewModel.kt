// app/src/main/java/com/example/app_lmao/presentation/ProductoViewModel.kt
package com.example.app_lmao.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.app_lmao.data.AppDatabase
import com.example.app_lmao.model.Producto
import com.example.app_lmao.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ProductoUIState(
    val isLoading: Boolean = false,
    val productos: List<Producto> = emptyList(),
    val error: String? = null
)

class ProductoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ProductoRepository
    
    // Estado para la búsqueda
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    // Estado para errores o carga
    private val _uiState = MutableStateFlow(ProductoUIState(isLoading = true))
    val uiState: StateFlow<ProductoUIState>

    init {
        val productoDao = AppDatabase.getDatabase(application).productoDao()
        repository = ProductoRepository(productoDao)

        // Flujo combinado que reacciona a la búsqueda Y a los cambios en la BD
        uiState = combine(
            repository.todosLosProductos,
            _searchQuery
        ) { productos, query ->
            val productosFiltrados = if (query.isBlank()) {
                productos
            } else {
                productos.filter {
                    it.nombre.contains(query, ignoreCase = true) || 
                    it.descripcion.contains(query, ignoreCase = true)
                }
            }
            ProductoUIState(productos = productosFiltrados, isLoading = false)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ProductoUIState(isLoading = true)
        )
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun agregarProducto(producto: Producto) = viewModelScope.launch {
        repository.agregarProducto(producto)
    }

    fun eliminarProducto(id: Int) = viewModelScope.launch {
        repository.eliminarProducto(id)
    }

    fun actualizarProducto(producto: Producto) = viewModelScope.launch {
        repository.actualizarProducto(producto)
    }
}