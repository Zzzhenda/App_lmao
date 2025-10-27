package com.example.lmao.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lmao.data.model.Pastel

@Composable
fun DetallePastelScreen(pastel: Pastel) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = pastel.nombre, style = MaterialTheme.typography.titleLarge)
        Text(text = pastel.descripcion)
        Text("Precio: ${pastel.precioFormateado}")
        Text("Stock: ${pastel.stock}")
    }
}
