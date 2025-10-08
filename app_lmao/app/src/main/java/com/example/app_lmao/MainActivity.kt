package com.example.app_lmao

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.app_lmao.screens.AgregarProductoScreen
import com.example.app_lmao.screens.ProductosScreen
import com.example.app_lmao.ui.theme.App_lmaoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App_lmaoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    var currentScreen by remember { mutableStateOf("productos") }

    when (currentScreen) {
        "productos" -> ProductosScreen(
            onAddProduct = { currentScreen = "agregar" }
        )
        "agregar" -> AgregarProductoScreen(
            onBack = { currentScreen = "productos" }
        )
    }
}

// Updated Greeting composable for reference
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    App_lmaoTheme {
        Greeting("Android")
    }
}