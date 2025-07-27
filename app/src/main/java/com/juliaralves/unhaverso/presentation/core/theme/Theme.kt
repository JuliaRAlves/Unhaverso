package com.juliaralves.unhaverso.presentation.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = White2,
    onPrimary = Purple2,
    primaryContainer = Purple2,
    secondaryContainer = Purple2,
    surface = Purple1,
    onSurface = White2,
    onPrimaryContainer = White2,
    tertiaryContainer = White1,
    onTertiaryContainer = Black2,
    onSurfaceVariant = White2,
    onSecondaryContainer = White2,
    outline = White3,
    outlineVariant = White3
)

private val LightColorScheme = lightColorScheme(
    primary = Pink1,
    onPrimary = White2,
    primaryContainer = White1,
    secondaryContainer = Pink1,
    surface = Pink2,
    onSurface = Black2,
    onPrimaryContainer = Pink1,
    tertiaryContainer = White1,
    onTertiaryContainer = Black2,
    onSurfaceVariant = Pink1,
    onSecondaryContainer = White2,
    outline = White3,
    outlineVariant = White3
)

@Composable
fun UnhaversoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}