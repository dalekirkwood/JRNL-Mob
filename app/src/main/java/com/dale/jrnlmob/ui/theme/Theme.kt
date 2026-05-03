package com.dale.jrnlmob.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

enum class ThemeMode { LIGHT, DARK, SYSTEM }
enum class DarkFlavor { FRAPPE, MACCHIATO, MOCHA }

private val LatteScheme = lightColorScheme(
    primary = CatppuccinLatte.Mauve,
    onPrimary = CatppuccinLatte.Base,
    primaryContainer = CatppuccinLatte.Surface1,
    onPrimaryContainer = CatppuccinLatte.Mauve,
    secondary = CatppuccinLatte.Blue,
    onSecondary = CatppuccinLatte.Base,
    secondaryContainer = CatppuccinLatte.Surface0,
    onSecondaryContainer = CatppuccinLatte.Blue,
    tertiary = CatppuccinLatte.Green,
    onTertiary = CatppuccinLatte.Base,
    tertiaryContainer = CatppuccinLatte.Surface0,
    onTertiaryContainer = CatppuccinLatte.Green,
    error = CatppuccinLatte.Red,
    onError = CatppuccinLatte.Base,
    errorContainer = CatppuccinLatte.Surface0,
    onErrorContainer = CatppuccinLatte.Red,
    background = CatppuccinLatte.Base,
    onBackground = CatppuccinLatte.Text,
    surface = CatppuccinLatte.Mantle,
    onSurface = CatppuccinLatte.Text,
    surfaceVariant = CatppuccinLatte.Surface0,
    onSurfaceVariant = CatppuccinLatte.Subtext1,
    outline = CatppuccinLatte.Overlay0,
    outlineVariant = CatppuccinLatte.Surface1,
    inverseSurface = CatppuccinMocha.Base,
    inverseOnSurface = CatppuccinMocha.Text
)

private val FrappeScheme = darkColorScheme(
    primary = CatppuccinFrappe.Mauve,
    onPrimary = CatppuccinFrappe.Crust,
    primaryContainer = CatppuccinFrappe.Surface1,
    onPrimaryContainer = CatppuccinFrappe.Mauve,
    secondary = CatppuccinFrappe.Blue,
    onSecondary = CatppuccinFrappe.Crust,
    secondaryContainer = CatppuccinFrappe.Surface0,
    onSecondaryContainer = CatppuccinFrappe.Blue,
    tertiary = CatppuccinFrappe.Green,
    onTertiary = CatppuccinFrappe.Crust,
    tertiaryContainer = CatppuccinFrappe.Surface0,
    onTertiaryContainer = CatppuccinFrappe.Green,
    error = CatppuccinFrappe.Red,
    onError = CatppuccinFrappe.Crust,
    errorContainer = CatppuccinFrappe.Surface0,
    onErrorContainer = CatppuccinFrappe.Red,
    background = CatppuccinFrappe.Base,
    onBackground = CatppuccinFrappe.Text,
    surface = CatppuccinFrappe.Mantle,
    onSurface = CatppuccinFrappe.Text,
    surfaceVariant = CatppuccinFrappe.Surface0,
    onSurfaceVariant = CatppuccinFrappe.Subtext1,
    outline = CatppuccinFrappe.Overlay0,
    outlineVariant = CatppuccinFrappe.Surface1,
    inverseSurface = CatppuccinLatte.Base,
    inverseOnSurface = CatppuccinLatte.Text
)

private val MacchiatoScheme = darkColorScheme(
    primary = CatppuccinMacchiato.Mauve,
    onPrimary = CatppuccinMacchiato.Crust,
    primaryContainer = CatppuccinMacchiato.Surface1,
    onPrimaryContainer = CatppuccinMacchiato.Mauve,
    secondary = CatppuccinMacchiato.Blue,
    onSecondary = CatppuccinMacchiato.Crust,
    secondaryContainer = CatppuccinMacchiato.Surface0,
    onSecondaryContainer = CatppuccinMacchiato.Blue,
    tertiary = CatppuccinMacchiato.Green,
    onTertiary = CatppuccinMacchiato.Crust,
    tertiaryContainer = CatppuccinMacchiato.Surface0,
    onTertiaryContainer = CatppuccinMacchiato.Green,
    error = CatppuccinMacchiato.Red,
    onError = CatppuccinMacchiato.Crust,
    errorContainer = CatppuccinMacchiato.Surface0,
    onErrorContainer = CatppuccinMacchiato.Red,
    background = CatppuccinMacchiato.Base,
    onBackground = CatppuccinMacchiato.Text,
    surface = CatppuccinMacchiato.Mantle,
    onSurface = CatppuccinMacchiato.Text,
    surfaceVariant = CatppuccinMacchiato.Surface0,
    onSurfaceVariant = CatppuccinMacchiato.Subtext1,
    outline = CatppuccinMacchiato.Overlay0,
    outlineVariant = CatppuccinMacchiato.Surface1,
    inverseSurface = CatppuccinLatte.Base,
    inverseOnSurface = CatppuccinLatte.Text
)

private val MochaScheme = darkColorScheme(
    primary = CatppuccinMocha.Mauve,
    onPrimary = CatppuccinMocha.Crust,
    primaryContainer = CatppuccinMocha.Surface1,
    onPrimaryContainer = CatppuccinMocha.Mauve,
    secondary = CatppuccinMocha.Blue,
    onSecondary = CatppuccinMocha.Crust,
    secondaryContainer = CatppuccinMocha.Surface0,
    onSecondaryContainer = CatppuccinMocha.Blue,
    tertiary = CatppuccinMocha.Green,
    onTertiary = CatppuccinMocha.Crust,
    tertiaryContainer = CatppuccinMocha.Surface0,
    onTertiaryContainer = CatppuccinMocha.Green,
    error = CatppuccinMocha.Red,
    onError = CatppuccinMocha.Crust,
    errorContainer = CatppuccinMocha.Surface0,
    onErrorContainer = CatppuccinMocha.Red,
    background = CatppuccinMocha.Base,
    onBackground = CatppuccinMocha.Text,
    surface = CatppuccinMocha.Mantle,
    onSurface = CatppuccinMocha.Text,
    surfaceVariant = CatppuccinMocha.Surface0,
    onSurfaceVariant = CatppuccinMocha.Subtext1,
    outline = CatppuccinMocha.Overlay0,
    outlineVariant = CatppuccinMocha.Surface1,
    inverseSurface = CatppuccinLatte.Base,
    inverseOnSurface = CatppuccinLatte.Text
)

private fun darkSchemeFor(flavor: DarkFlavor): ColorScheme = when (flavor) {
    DarkFlavor.FRAPPE -> FrappeScheme
    DarkFlavor.MACCHIATO -> MacchiatoScheme
    DarkFlavor.MOCHA -> MochaScheme
}

@Composable
fun JrnlMobTheme(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    darkFlavor: DarkFlavor = DarkFlavor.MOCHA,
    content: @Composable () -> Unit
) {
    val useDark = when (themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }

    val colorScheme = if (useDark) darkSchemeFor(darkFlavor) else LatteScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                @Suppress("DEPRECATION")
                window.statusBarColor = colorScheme.background.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !useDark
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = JrnlTypography,
        content = content
    )
}
