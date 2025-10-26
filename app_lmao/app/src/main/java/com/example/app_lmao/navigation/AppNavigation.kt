// app/src/main/java/com/example/app_lmao/navigation/AppNavigation.kt
package com.example.app_lmao.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_lmao.screens.AgregarProductoScreen
import com.example.app_lmao.screens.ListaProductosScreen

object AppScreens {
    const val LISTA_PRODUCTOS = "lista_productos"
    const val AGREGAR_PRODUCTO = "agregar_producto"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreens.LISTA_PRODUCTOS
    ) {
        composable(AppScreens.LISTA_PRODUCTOS) {
            ListaProductosScreen(
                onAddProductClick = {
                    navController.navigate(AppScreens.AGREGAR_PRODUCTO)
                }
            )
        }
        composable(AppScreens.AGREGAR_PRODUCTO) {
            AgregarProductoScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}