package com.example.lmao.data.repository

import com.example.lmao.data.model.Pastel

class PastelRepository {

    private val listaPasteles = mutableListOf(
        Pastel(1, "Tres Leches", "Clásico pastel húmedo", 12000.0, "Tradicional", 10, "url1"),
        Pastel(2, "Selva Negra", "Bizcocho de chocolate con cerezas", 15000.0, "Chocolate", 5, "url2"),
        Pastel(3, "Cheesecake", "Tarta de queso con frutilla", 13000.0, "Queso", 8, "url3")
    )

    fun obtenerPasteles(): List<Pastel> = listaPasteles

    fun buscarPorNombre(query: String): List<Pastel> =
        listaPasteles.filter { it.nombre.contains(query, ignoreCase = true) }

    fun obtenerPorCategoria(categoria: String): List<Pastel> =
        listaPasteles.filter { it.categoria.equals(categoria, ignoreCase = true) }

    fun aplicarDescuento(precio: Double, edad: Int, codigo: String): Double {
        var total = precio
        if (edad > 50) total *= 0.5
        if (codigo.equals("FELICES50", ignoreCase = true)) total *= 0.9
        return total
    }

    fun agregarPastel(pastel: Pastel) {
        listaPasteles.add(pastel)
    }
}
