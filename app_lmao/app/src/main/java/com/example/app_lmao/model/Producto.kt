// app/src/main/java/com/example/app_lmao/model/Producto.kt
package com.example.app_lmao.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.NumberFormat
import java.util.Locale

@Entity(tableName = "productos")
data class Producto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val codigo: String, // Requerido por el caso
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val stock: Int,
    val categoria: String,
    val imagenUri: String? = null // Para Recurso Nativo (IE 2.4.1)
) {
    val disponible: Boolean
        get() = stock > 0

    val precioFormateado: String
        get() {
            val format = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
            return format.format(precio)
        }
}