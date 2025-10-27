package com.example.lmao.presentation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lmao.data.model.Pastel
import com.example.lmao.presentation.viewmodel.PastelViewModel
import com.example.lmao.presentation.viewmodel.PastelUIState
import com.example.lmao.ui.screens.DetallePastelScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: PastelViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "lista") {
        composable("lista") {
            PastelesScreen(
                uiState = uiState,
                onClick = { pastel ->
                    navController.navigate("detalle/${pastel.id}")
                },
                onRefresh = { viewModel.cargarPasteles() },
                onSearch = { query -> viewModel.buscarPasteles(query) },
                onFilterCategory = { categoria ->
                    if (categoria != null) viewModel.filtrarPorCategoria(categoria)
                    else viewModel.cargarPasteles()
                }
            )
        }

        composable("detalle/{id}") { backStackEntry ->
            val pastelId = backStackEntry.arguments?.getString("id")?.toInt() ?: 0
            val pastel = uiState.pasteles.firstOrNull { it.id == pastelId }
            pastel?.let { DetallePastelScreen(it) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PastelesScreen(
    uiState: PastelUIState,
    onClick: (Pastel) -> Unit,
    onRefresh: () -> Unit,
    onSearch: (String) -> Unit,
    onFilterCategory: (String?) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val categorias = remember { uiState.pasteles.map { it.categoria }.distinct() }
    var categoriaSeleccionada by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Pasteles") },
                actions = {
                    IconButton(onClick = { onRefresh() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Recargar")
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
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                categorias.forEach { cat ->
                    FilterChip(
                        selected = categoriaSeleccionada == cat,
                        onClick = {
                            if (categoriaSeleccionada == cat) {
                                categoriaSeleccionada = null
                                onFilterCategory(null)
                            } else {
                                categoriaSeleccionada = cat
                                onFilterCategory(cat)
                            }
                        },
                        label = { Text(cat) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    onSearch(it)
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Buscar pasteles...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            when {
                uiState.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                uiState.errorMessage != null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = uiState.errorMessage ?: "Error desconocido",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
                uiState.pasteles.isEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No hay pasteles disponibles")
                    }
                }
                else -> {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(uiState.pasteles) { pastel ->
                            PastelCard(pastel, onClick)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PastelCard(pastel: Pastel, onClick: (Pastel) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(pastel) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = pastel.nombre, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = pastel.descripcion, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Precio: $${pastel.precio}", style = MaterialTheme.typography.titleLarge)
                Surface(
                    color = if (pastel.stock > 0) MaterialTheme.colorScheme.secondaryContainer
                    else MaterialTheme.colorScheme.errorContainer,
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = if (pastel.stock > 0) "Stock: ${pastel.stock}" else "Sin stock",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}
