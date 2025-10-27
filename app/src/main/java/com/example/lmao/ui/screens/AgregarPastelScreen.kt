package com.example.lmao.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lmao.data.model.Pastel
import com.example.lmao.presentation.viewmodel.PastelViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarPastelScreen(viewModel: PastelViewModel) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var imagenUrl by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Agregar Pastel", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = precio,
            onValueChange = { precio = it },
            label = { Text("Precio") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = categoria,
            onValueChange = { categoria = it },
            label = { Text("Categoría") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = stock,
            onValueChange = { stock = it },
            label = { Text("Stock") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = imagenUrl,
            onValueChange = { imagenUrl = it },
            label = { Text("URL Imagen") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val nuevoPastel = Pastel(
                    id = (viewModel.uiState.value.pasteles.maxOfOrNull { it.id } ?: 0) + 1,
                    nombre = nombre,
                    descripcion = descripcion,
                    precio = precio.toDoubleOrNull() ?: 0.0,
                    categoria = categoria,
                    stock = stock.toIntOrNull() ?: 0,
                    imagenUrl = imagenUrl
                )
                viewModel.agregarPastel(nuevoPastel)
                // Reset campos
                nombre = ""
                descripcion = ""
                precio = ""
                categoria = ""
                stock = ""
                imagenUrl = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar")
        }
    }
}
