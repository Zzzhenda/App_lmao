package com.example.lmao.data.model

data class Pastel(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val categoria: String,
    val stock: Int,
    val imagenUrl: String
) {
    val precioFormateado: String
        get() = "$%.2f".format(precio)
}
