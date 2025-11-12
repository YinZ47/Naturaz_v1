package com.naturaz.bd.presentation.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val NaturazDarkColorScheme = darkColorScheme(
    primary = NaturazGreen,
    onPrimary = NaturazOnPrimary,
    secondary = NaturazLightGreen,
    onSecondary = NaturazOnSecondary,
    error = NaturazError,
    background = NaturazDarkText.copy(alpha = 0.95f),
    onBackground = NaturazSurface,
    surface = NaturazDarkText.copy(alpha = 0.95f),
    onSurface = NaturazSurface,
    tertiary = NaturazSky
)

private val NaturazLightColorScheme = lightColorScheme(
    primary = NaturazGreen,
    onPrimary = NaturazOnPrimary,
    secondary = NaturazLightGreen,
    onSecondary = NaturazOnSecondary,
    tertiary = NaturazSky,
    error = NaturazError,
    background = NaturazBackground,
    onBackground = NaturazDarkText,
    surface = NaturazSurface,
    onSurface = NaturazDarkText
)

@Composable
fun NaturazTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    useDynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme: ColorScheme = when {
        useDynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (useDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        useDarkTheme -> NaturazDarkColorScheme
        else -> NaturazLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = NaturazTypography,
        shapes = NaturazShapes,
        content = content
    )
}
