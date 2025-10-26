// app/src/main/java/com/example/app_lmao/repository/ProductoRepository.kt
package com.example.app_lmao.repository

import com.example.app_lmao.data.ProductoDao
import com.example.app_lmao.model.Producto
import kotlinx.coroutines.flow.Flow

class ProductoRepository(private val productoDao: ProductoDao) {

    val todosLosProductos: Flow<List<Producto>> = productoDao.getAll()

    fun buscarProductos(query: String): Flow<List<Producto>> {
        return productoDao.search(query)
    }

    suspend fun agregarProducto(producto: Producto) {
        productoDao.insert(producto)
    }

    suspend fun eliminarProducto(id: Int) {
        productoDao.deleteById(id)
    }

    suspend fun actualizarProducto(producto: Producto) {
        productoDao.update(producto)
    }
}