package com.fiap.alaganao.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = AzulAgua,
    onPrimary = Color.White,
    primaryContainer = AzulFundo,
    onPrimaryContainer = AzulEscuro,
    secondary = LaranjaAlerta,
    onSecondary = Color.White,
    background = FundoClaro,
    onBackground = TextoEscuro,
    surface = Color.White,
    onSurface = TextoEscuro,
    surfaceVariant = AzulFundo,
    onSurfaceVariant = CinzaTexto
)

private val DarkColors = darkColorScheme(
    primary = AzulAguaClaro,
    onPrimary = AzulEscuro,
    primaryContainer = AzulEscuro,
    onPrimaryContainer = AzulFundo,
    secondary = LaranjaAlerta,
    onSecondary = Color.White,
    background = FundoEscuro,
    onBackground = Color(0xFFE3EDF5),
    surface = SuperficieEscura,
    onSurface = Color(0xFFE3EDF5),
    surfaceVariant = Color(0xFF24384A),
    onSurfaceVariant = Color(0xFFB9C8D6)
)

@Composable
fun AlagaNaoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}
