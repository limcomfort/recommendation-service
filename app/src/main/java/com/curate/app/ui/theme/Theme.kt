package com.curate.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    onPrimary = Surface,
    secondary = SuccessGreen,
    onSecondary = Surface,
    tertiary = SuccessGreen,
    background = Canvas,
    onBackground = PrimaryInk,
    surface = Surface,
    onSurface = PrimaryInk,
    surfaceVariant = BlueTint,
    onSurfaceVariant = MutedInk,
    outline = Border,
    outlineVariant = Border,
    error = Color(0xFFDC2626),
)

@Composable
fun CurateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    // The Curate design system is light-first; keep it light in dark mode too.
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = CurateTypography,
        shapes = CurateShapes,
        content = content,
    )
}
