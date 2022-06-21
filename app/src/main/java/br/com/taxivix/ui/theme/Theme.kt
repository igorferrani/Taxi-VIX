package br.com.taxivix.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Color(0xffc39b00),
    primaryVariant = Purple700,
    secondary = Color(0xff0068af),
    onPrimary = Color(0xFFFFFFFF)
)

private val LightColorPalette = lightColors(
    primary = Color(0xfffbcb0c),
    primaryVariant = Color(0xffc19900),
    secondary = Color(0xff62c6ff),
    onPrimary = Color(0xFF000000)

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun TaxiVIXTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}