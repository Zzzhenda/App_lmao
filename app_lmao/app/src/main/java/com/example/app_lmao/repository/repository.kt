package com.example.app_lmao.repository

import com.example.app_lmao.model.Producto
import kotlinx.coroutines.delay

class Repository {

    // Sample data
    private val productos = mutableListOf(
        Producto(1, "Leemonpie", "Pie de Limón", 4.99, 2, "Pie"),
        Producto(2, "Chocolate Lava", "Pastel de Chocolate con relleno de lava", 6.99, 5, "Pastel"),
        Producto(3, "Tarta de Fresas", "Deliciosa tarta con fresas frescas", 7.49, 3, "Tarta"),
        Producto(
            4,
            "Muffin de Arándano",
            "Muffin de arándano suave y esponjoso",
            2.99,
            10,
            "Muffin"
        ),
        Producto(5, "Galletas de Avena", "Galletas de avena caseras", 3.49, 20, "Galleta")
    )

    // Get all products
    suspend fun obtenerProductos(): List<Producto> {
        println("Obteniendo productos...")  // Log output for testing
        delay(500)  // Simulate network or database delay
        return productos.toList()  // Return a copy of the list
    }

    // Search products by name or description
    suspend fun buscarProductos(query: String): List<Producto> {
        delay(500)  // Simulate delay
        return productos.filter {
            it.nombre.contains(query, ignoreCase = true) || it.descripcion.contains(query, ignoreCase = true)
        }
    }

    // Delete a product by ID
    suspend fun eliminarProducto(id: Int): List<Producto> {
        // Remove the product with the given ID
        productos.removeAll { it.id == id }
        delay(500)  // Simulate delay
        return productos  // Return the updated list
    }

    // Add a new product
    suspend fun agregarProducto(producto: Producto): List<Producto> {
        productos.add(producto)
        delay(500)  // Simulate delay
        return productos
    }

    // Update an existing product by ID
    suspend fun actualizarProducto(id: Int, updatedProduct: Producto): List<Producto> {
        val index = productos.indexOfFirst { it.id == id }
        if (index != -1) {
            productos[index] = updatedProduct
        }
        delay(500)  // Simulate delay
        return productos
    }



    // BUSCAR POR CATEGORIA
    suspend fun obtenerPorCategoria (categoria: String) : List<Producto>{
        delay(1000)
        return productos.filter {
            it.categoria == categoria
        }
    }
}
