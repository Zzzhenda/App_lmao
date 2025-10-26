// app/src/main/java/com/example/app_lmao/screens/AgregarProductoScreen.kt
package com.example.app_lmao.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.app_lmao.model.Producto
import com.example.app_lmao.presentation.ProductoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarProductoScreen(
    onBack: () -> Unit,
    viewModel: ProductoViewModel = viewModel(factory = viewModelFactory { ProductoViewModel(it.application) })
) {
    // Estado para los campos del formulario
    var codigo by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var imagenUri by remember { mutableStateOf<Uri?>(null) }

    // Estado para la validación (IE 2.1.1)
    var nombreError by remember { mutableStateOf(false) }
    var precioError by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    // Launcher para seleccionar imagen (IE 2.4.1)
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imagenUri = uri
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agregar Producto") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            
            // Campo Nombre con Validación
            OutlinedTextField(
                value = nombre,
                onValueChange = { 
                    nombre = it
                    nombreError = it.isBlank() 
                },
                label = { Text("Nombre*") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                isError = nombreError,
                trailingIcon = { if (nombreError) Icon(Icons.Default.Error, "Error") },
                supportingText = { if (nombreError) Text("El nombre es obligatorio") }
            )
            
            // ... (Otros campos: Codigo, Descripcion, Categoria)
            OutlinedTextField(value = codigo, onValueChange = { codigo = it }, label = { Text("Código") }, modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp))
            OutlinedTextField(value = descripcion, onValueChange = { descripcion = it }, label = { Text("Descripción") }, modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp))
            OutlinedTextField(value = categoria, onValueChange = { categoria = it }, label = { Text("Categoría") }, modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp))

            // Campo Precio con Validación
            OutlinedTextField(
                value = precio,
                onValueChange = { 
                    precio = it
                    precioError = it.toDoubleOrNull() == null
                },
                label = { Text("Precio*") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = precioError,
                trailingIcon = { if (precioError) Icon(Icons.Default.Error, "Error") },
                supportingText = { if (precioError) Text("Debe ser un número válido") }
            )
            
            // Campo Stock
            OutlinedTextField(value = stock, onValueChange = { stock = it }, label = { Text("Stock") }, modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))

            // Selector de Imagen (IE 2.4.1)
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { imagePickerLauncher.launch("image/*") }) {
                    Text("Seleccionar Imagen")
                }
                imagenUri?.let {
                    Image(
                        painter = rememberAsyncImagePainter(it),
                        contentDescription = "Imagen seleccionada",
                        modifier = Modifier.size(60.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botones de Acción
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(onClick = onBack, modifier = Modifier.weight(1f)) {
                    Text("Cancelar")
                }
                Button(
                    onClick = {
                        nombreError = nombre.isBlank()
                        precioError = precio.toDoubleOrNull() == null

                        if (!nombreError && !precioError) {
                            val nuevoProducto = Producto(
                                codigo = codigo.ifBlank { "N/A" },
                                nombre = nombre,
                                descripcion = descripcion,
                                precio = precio.toDoubleOrNull() ?: 0.0,
                                stock = stock.toIntOrNull() ?: 0,
                                categoria = categoria.ifBlank { "General" },
                                imagenUri = imagenUri?.toString()
                            )
                            viewModel.agregarProducto(nuevoProducto)
                            onBack()
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Agregar")
                }
            }
        }
    }
}