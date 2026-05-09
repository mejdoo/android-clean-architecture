package com.mejdoo.clean.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Core color tokens
private val LightColors: Colors = lightColors(
    primary = Color(0xFF0B2545),
    primaryVariant = Color(0xFF3B82F6),
    secondary = Color(0xFFFF6B6B),
    background = Color(0xFFF8FAFC),
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color(0xFF0F172A),
    onSurface = Color(0xFF0F172A)
)

private val DarkColors: Colors = darkColors(
    primary = Color(0xFF3B82F6),
    primaryVariant = Color(0xFF0B2545),
    secondary = Color(0xFFFF6B6B),
    background = Color(0xFF0B1220),
    surface = Color(0xFF07122B),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color(0xFFE6EEF8),
    onSurface = Color(0xFFE6EEF8)
)

// Simple typography setup (use defaults)
private val AppTypography = Typography()

// Simple shapes
private val AppShapes = Shapes()

@Composable
fun CleanTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colors = colors,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}
