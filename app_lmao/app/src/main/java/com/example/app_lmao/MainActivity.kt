// app/src/main/java/com/example/app_lmao/MainActivity.kt
package com.example.app_lmao // PAQUETE CORREGIDO

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.app_lmao.navigation.AppNavigation // IMPORT CORREGIDO
import com.example.app_lmao.ui.theme.App_lmaoTheme // IMPORT CORREGIDO

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App_lmaoTheme { // Aplicar el tema
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Cargar el sistema de navegación
                    AppNavigation()
                }
            }
        }
    }
}