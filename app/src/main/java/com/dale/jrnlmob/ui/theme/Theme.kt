package com.dale.jrnlmob.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = SoftBrown,
    onPrimary = WarmWhite,
    primaryContainer = WarmBeige,
    onPrimaryContainer = DeepBrown,
    secondary = SoftGreen,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE8F5E9),
    onSecondaryContainer = Color(0xFF33691E),
    tertiary = SunsetOrange,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFFF3E0),
    onTertiaryContainer = Color(0xFFBF360C),
    background = WarmBeige,
    onBackground = DeepBrown,
    surface = WarmWhite,
    onSurface = DeepBrown,
    surfaceVariant = Color(0xFFF0EBE3),
    onSurfaceVariant = SoftBrown,
    outline = Color(0xFFD7CCC8),
    outlineVariant = Color(0xFFEFEBE4),
    inverseSurface = DarkCard,
    inverseOnSurface = DarkWarmText
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFD4B896),
    onPrimary = Color(0xFF3E2A1A),
    primaryContainer = Color(0xFF5D4037),
    onPrimaryContainer = Color(0xFFF0D9B5),
    secondary = SoftGreen,
    onSecondary = Color(0xFF1B5E20),
    secondaryContainer = Color(0xFF33691E),
    onSecondaryContainer = Color(0xFFE8F5E9),
    tertiary = SunsetOrange,
    onTertiary = Color(0xFF3E1000),
    tertiaryContainer = Color(0xFFBF360C),
    onTertiaryContainer = Color(0xFFFFF3E0),
    background = DarkWarmBg,
    onBackground = DarkWarmText,
    surface = DarkSurface,
    onSurface = DarkWarmText,
    surfaceVariant = DarkCard,
    onSurfaceVariant = DarkMutedText,
    outline = Color(0xFF5A4F42),
    outlineVariant = Color(0xFF3D342A),
    inverseSurface = WarmBeige,
    inverseOnSurface = DeepBrown
)

@Composable
fun JrnlMobTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                @Suppress("DEPRECATION")
                window.statusBarColor = colorScheme.background.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = JrnlTypography,
        content = content
    )
}
