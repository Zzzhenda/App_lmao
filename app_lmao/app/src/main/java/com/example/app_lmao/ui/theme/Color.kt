// app/src/main/java/com/example/app_lmao/ui/theme/Color.kt
package com.example.app_lmao.ui.theme

import androidx.compose.ui.graphics.Color

// Colores del Caso "Pastelería 1000 Sabores"
val CremaPastel = Color(0xFFFFF5E1)
val RosaSuave = Color(0xFFFBC0CB) // Modificado para mejor visibilidad (FFC0CB es muy claro)
val Chocolate = Color(0xFF8B4513)
val MarronOscuro = Color(0xFF5D4037)
val GrisClaro = Color(0xFFB0BEC5)

// Definición para LightTheme
val light_primary = Chocolate
val light_onPrimary = Color.White
val light_secondary = RosaSuave
val light_onSecondary = MarronOscuro
val light_background = CremaPastel
val light_onBackground = MarronOscuro
val light_surface = CremaPastel
val light_onSurface = MarronOscuro

// Definición para DarkTheme (Adaptación)
val dark_primary = RosaSuave
val dark_onPrimary = MarronOscuro
val dark_secondary = Chocolate
val dark_onSecondary = Color.White
val dark_background = Color(0xFF1c1b1f)
val dark_onBackground = Color(0xFFe6e1e5)
val dark_surface = Color(0xFF1c1b1f)
val dark_onSurface = Color(0xFFe6e1e5)