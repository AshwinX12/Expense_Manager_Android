package com.expensemanager.app.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.expensemanager.app.data.db.entity.ThemeMode
import com.expensemanager.app.data.db.entity.ThemeStyle

/** Whether the resolved theme is dark, independent of the OS setting (themeMode may force it). */
val LocalIsDarkTheme = staticCompositionLocalOf { false }

/**
 * Material3's container/surface roles default to the baseline purple palette when a scheme
 * doesn't set them, which leaks lavender menus and purple-tinted sheets into these themes.
 * This derives the whole ramp from the theme's own surface color instead.
 */
private fun ColorScheme.withNeutralSurfaces(base: Color, dark: Boolean): ColorScheme {
    val away = if (dark) Color.Black else Color.White
    val toward = if (dark) Color.White else Color.Black
    return copy(
        surfaceContainerLowest = lerp(base, away, 0.30f),
        surfaceContainerLow = lerp(base, away, 0.12f),
        surfaceContainer = base,
        surfaceContainerHigh = lerp(base, toward, 0.05f),
        surfaceContainerHighest = lerp(base, toward, 0.10f),
        surfaceDim = lerp(base, Color.Black, if (dark) 0.20f else 0.08f),
        surfaceBright = lerp(base, Color.White, if (dark) 0.08f else 0.04f),
        inverseSurface = if (dark) Color(0xFFE8E8E8) else Color(0xFF2B2B2B),
        inverseOnSurface = if (dark) Color(0xFF1A1A1A) else Color(0xFFF2F2F2),
        scrim = Color.Black
    )
}

// ---- Classic ----
private val LightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = Color.White,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    secondary = Color(0xFF625B71),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE8DEF8),
    onSecondaryContainer = Color(0xFF1D192B),
    tertiary = Color(0xFF4ECDC4),
    onTertiary = Color.White,
    background = BackgroundLight,
    onBackground = TextPrimaryLight,
    surface = SurfaceLight,
    onSurface = TextPrimaryLight,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = TextSecondaryLight,
    error = BudgetDanger,
    onError = Color.White,
    outline = Color(0xFFCCCCDD),
    outlineVariant = Color(0xFFE0E0EE)
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = Color(0xFF1A1640),
    primaryContainer = Color(0xFF2D2870),
    onPrimaryContainer = Color(0xFFE8E6FF),
    secondary = Color(0xFFCCC2DC),
    onSecondary = Color(0xFF332D41),
    secondaryContainer = Color(0xFF4A4458),
    onSecondaryContainer = Color(0xFFE8DEF8),
    tertiary = Color(0xFF4ECDC4),
    onTertiary = Color(0xFF003734),
    background = BackgroundDark,
    onBackground = TextPrimaryDark,
    surface = SurfaceDark,
    onSurface = TextPrimaryDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = TextSecondaryDark,
    error = Color(0xFFFF6B8A),
    onError = Color(0xFF601410),
    outline = Color(0xFF444460),
    outlineVariant = Color(0xFF333348)
)

// ---- Warm Paper ----
private val WarmPaperLightColorScheme = lightColorScheme(
    primary = WarmPaperColors.AccentLight,
    onPrimary = Color.White,
    primaryContainer = WarmPaperColors.SurfaceAltLight,
    onPrimaryContainer = WarmPaperColors.TextPrimaryLight,
    secondary = WarmPaperColors.NeutralLight,
    onSecondary = Color.White,
    secondaryContainer = WarmPaperColors.SurfaceAltLight,
    onSecondaryContainer = WarmPaperColors.TextPrimaryLight,
    tertiary = WarmPaperColors.NeutralLight,
    onTertiary = Color.White,
    background = WarmPaperColors.BackgroundLight,
    onBackground = WarmPaperColors.TextPrimaryLight,
    surface = WarmPaperColors.SurfaceLight,
    onSurface = WarmPaperColors.TextPrimaryLight,
    surfaceVariant = WarmPaperColors.SurfaceAltLight,
    onSurfaceVariant = WarmPaperColors.TextSecondaryLight,
    error = WarmPaperColors.StatusOver,
    onError = Color.White,
    outline = WarmPaperColors.DividerLight,
    outlineVariant = WarmPaperColors.DividerLight,
    surfaceTint = Color.Transparent
).withNeutralSurfaces(WarmPaperColors.BackgroundLight, dark = false)

private val WarmPaperDarkColorScheme = darkColorScheme(
    primary = WarmPaperColors.AccentDark,
    onPrimary = WarmPaperColors.BackgroundDark,
    primaryContainer = WarmPaperColors.SurfaceAltDark,
    onPrimaryContainer = WarmPaperColors.TextPrimaryDark,
    secondary = WarmPaperColors.NeutralDark,
    onSecondary = WarmPaperColors.BackgroundDark,
    secondaryContainer = WarmPaperColors.SurfaceAltDark,
    onSecondaryContainer = WarmPaperColors.TextPrimaryDark,
    tertiary = WarmPaperColors.NeutralDark,
    onTertiary = WarmPaperColors.BackgroundDark,
    background = WarmPaperColors.BackgroundDark,
    onBackground = WarmPaperColors.TextPrimaryDark,
    surface = WarmPaperColors.SurfaceDark,
    onSurface = WarmPaperColors.TextPrimaryDark,
    surfaceVariant = WarmPaperColors.SurfaceAltDark,
    onSurfaceVariant = WarmPaperColors.TextSecondaryDark,
    error = WarmPaperColors.StatusOver,
    onError = Color.White,
    outline = WarmPaperColors.DividerDark,
    outlineVariant = WarmPaperColors.DividerDark,
    surfaceTint = Color.Transparent
).withNeutralSurfaces(WarmPaperColors.SurfaceDark, dark = true)

// ---- Editorial ----
private val EditorialLightColorScheme = lightColorScheme(
    primary = EditorialColors.AccentLight,
    onPrimary = Color.White,
    primaryContainer = EditorialColors.BackgroundLight,
    onPrimaryContainer = EditorialColors.TextPrimaryLight,
    secondary = EditorialColors.NeutralLight,
    onSecondary = Color.White,
    secondaryContainer = EditorialColors.BackgroundLight,
    onSecondaryContainer = EditorialColors.TextPrimaryLight,
    tertiary = EditorialColors.NeutralLight,
    onTertiary = Color.White,
    background = EditorialColors.BackgroundLight,
    onBackground = EditorialColors.TextPrimaryLight,
    surface = EditorialColors.BackgroundLight,
    onSurface = EditorialColors.TextPrimaryLight,
    surfaceVariant = EditorialColors.BackgroundLight,
    onSurfaceVariant = EditorialColors.TextSecondaryLight,
    error = EditorialColors.StatusOver,
    onError = Color.White,
    outline = EditorialColors.RuleLight,
    outlineVariant = EditorialColors.RuleLight,
    surfaceTint = Color.Transparent
).withNeutralSurfaces(EditorialColors.BackgroundLight, dark = false)

private val EditorialDarkColorScheme = darkColorScheme(
    primary = EditorialColors.AccentDark,
    onPrimary = EditorialColors.BackgroundDark,
    primaryContainer = EditorialColors.BackgroundDark,
    onPrimaryContainer = EditorialColors.TextPrimaryDark,
    secondary = EditorialColors.NeutralDark,
    onSecondary = EditorialColors.BackgroundDark,
    secondaryContainer = EditorialColors.BackgroundDark,
    onSecondaryContainer = EditorialColors.TextPrimaryDark,
    tertiary = EditorialColors.NeutralDark,
    onTertiary = EditorialColors.BackgroundDark,
    background = EditorialColors.BackgroundDark,
    onBackground = EditorialColors.TextPrimaryDark,
    surface = EditorialColors.BackgroundDark,
    onSurface = EditorialColors.TextPrimaryDark,
    surfaceVariant = EditorialColors.BackgroundDark,
    onSurfaceVariant = EditorialColors.TextSecondaryDark,
    error = EditorialColors.StatusOver,
    onError = Color.White,
    outline = EditorialColors.RuleDark,
    outlineVariant = EditorialColors.RuleDark,
    surfaceTint = Color.Transparent
).withNeutralSurfaces(EditorialColors.BackgroundDark, dark = true)

// ---- Memphis Geometric ----
private val MemphisLightColorScheme = lightColorScheme(
    primary = MemphisColors.AccentLight,
    onPrimary = Color.White,
    primaryContainer = MemphisColors.SurfaceLight,
    onPrimaryContainer = MemphisColors.TextPrimaryLight,
    secondary = MemphisColors.DecorativeBlue,
    onSecondary = Color.White,
    secondaryContainer = MemphisColors.SurfaceLight,
    onSecondaryContainer = MemphisColors.TextPrimaryLight,
    tertiary = MemphisColors.DecorativeTeal,
    onTertiary = Color.White,
    background = MemphisColors.BackgroundLight,
    onBackground = MemphisColors.TextPrimaryLight,
    surface = MemphisColors.SurfaceLight,
    onSurface = MemphisColors.TextPrimaryLight,
    surfaceVariant = MemphisColors.BackgroundLight,
    onSurfaceVariant = MemphisColors.TextSecondaryLight,
    error = MemphisColors.StatusOver,
    onError = Color.White,
    outline = MemphisColors.DividerLight,
    outlineVariant = MemphisColors.DividerLight,
    surfaceTint = Color.Transparent
).withNeutralSurfaces(MemphisColors.SurfaceLight, dark = false)

private val MemphisDarkColorScheme = darkColorScheme(
    primary = MemphisColors.AccentDark,
    onPrimary = Color.White,
    primaryContainer = MemphisColors.SurfaceDark,
    onPrimaryContainer = MemphisColors.TextPrimaryDark,
    secondary = MemphisColors.DecorativeBlue,
    onSecondary = Color.White,
    secondaryContainer = MemphisColors.SurfaceDark,
    onSecondaryContainer = MemphisColors.TextPrimaryDark,
    tertiary = MemphisColors.DecorativeTeal,
    onTertiary = Color.White,
    background = MemphisColors.BackgroundDark,
    onBackground = MemphisColors.TextPrimaryDark,
    surface = MemphisColors.SurfaceDark,
    onSurface = MemphisColors.TextPrimaryDark,
    surfaceVariant = MemphisColors.BackgroundDark,
    onSurfaceVariant = MemphisColors.TextSecondaryDark,
    error = MemphisColors.StatusOver,
    onError = Color.White,
    outline = MemphisColors.DividerDark,
    outlineVariant = MemphisColors.DividerDark,
    surfaceTint = Color.Transparent
).withNeutralSurfaces(MemphisColors.SurfaceDark, dark = true)

// ---- Terminal ----
private val TerminalLightColorScheme = lightColorScheme(
    primary = TerminalColors.AccentLight,
    onPrimary = Color.White,
    primaryContainer = TerminalColors.SurfaceLight,
    onPrimaryContainer = TerminalColors.TextPrimaryLight,
    secondary = TerminalColors.TextSecondaryLight,
    onSecondary = Color.White,
    secondaryContainer = TerminalColors.SurfaceLight,
    onSecondaryContainer = TerminalColors.TextPrimaryLight,
    tertiary = TerminalColors.WarningLight,
    onTertiary = Color.White,
    background = TerminalColors.BackgroundLight,
    onBackground = TerminalColors.TextPrimaryLight,
    surface = TerminalColors.SurfaceLight,
    onSurface = TerminalColors.TextPrimaryLight,
    surfaceVariant = TerminalColors.BackgroundLight,
    onSurfaceVariant = TerminalColors.TextSecondaryLight,
    error = TerminalColors.ErrorLight,
    onError = Color.White,
    outline = TerminalColors.RuleLight,
    outlineVariant = TerminalColors.RuleLight,
    surfaceTint = Color.Transparent
).withNeutralSurfaces(TerminalColors.SurfaceLight, dark = false)

private val TerminalDarkColorScheme = darkColorScheme(
    primary = TerminalColors.AccentDark,
    onPrimary = TerminalColors.BackgroundDark,
    primaryContainer = TerminalColors.SurfaceDark,
    onPrimaryContainer = TerminalColors.TextPrimaryDark,
    secondary = TerminalColors.TextSecondaryDark,
    onSecondary = TerminalColors.BackgroundDark,
    secondaryContainer = TerminalColors.SurfaceDark,
    onSecondaryContainer = TerminalColors.TextPrimaryDark,
    tertiary = TerminalColors.WarningDark,
    onTertiary = TerminalColors.BackgroundDark,
    background = TerminalColors.BackgroundDark,
    onBackground = TerminalColors.TextPrimaryDark,
    surface = TerminalColors.SurfaceDark,
    onSurface = TerminalColors.TextPrimaryDark,
    surfaceVariant = TerminalColors.BackgroundDark,
    onSurfaceVariant = TerminalColors.TextSecondaryDark,
    error = TerminalColors.ErrorDark,
    onError = TerminalColors.BackgroundDark,
    outline = TerminalColors.RuleDark,
    outlineVariant = TerminalColors.RuleDark,
    surfaceTint = Color.Transparent
).withNeutralSurfaces(TerminalColors.SurfaceDark, dark = true)

@Composable
fun ExpenseManagerTheme(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    themeStyle: ThemeStyle = ThemeStyle.CLASSIC,
    content: @Composable () -> Unit
) {
    // Read the system setting unconditionally: isSystemInDarkTheme() is a @Composable call,
    // so invoking it inside only one `when` branch changes the composition's group structure
    // when themeMode switches to/from SYSTEM. That discards the sibling subtree — including
    // the NavHost's back stack — which made changing the theme jump back to Home.
    val systemDark = isSystemInDarkTheme()

    // Terminal defaults to dark even before considering the OS preference —
    // only an explicit Light selection in ThemeMode overrides that.
    val darkTheme = when (themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> themeStyle == ThemeStyle.TERMINAL || systemDark
    }

    val colorScheme = when (themeStyle) {
        ThemeStyle.CLASSIC -> if (darkTheme) DarkColorScheme else LightColorScheme
        ThemeStyle.WARM_PAPER -> if (darkTheme) WarmPaperDarkColorScheme else WarmPaperLightColorScheme
        ThemeStyle.EDITORIAL -> if (darkTheme) EditorialDarkColorScheme else EditorialLightColorScheme
        ThemeStyle.MEMPHIS -> if (darkTheme) MemphisDarkColorScheme else MemphisLightColorScheme
        ThemeStyle.TERMINAL -> if (darkTheme) TerminalDarkColorScheme else TerminalLightColorScheme
    }

    val typography = when (themeStyle) {
        ThemeStyle.CLASSIC -> AppTypography
        ThemeStyle.WARM_PAPER -> WarmPaperTypography
        ThemeStyle.EDITORIAL -> EditorialTypography
        ThemeStyle.MEMPHIS -> MemphisTypography
        ThemeStyle.TERMINAL -> TerminalTypography
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            window.navigationBarColor = Color.Transparent.toArgb()
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !darkTheme
                isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    CompositionLocalProvider(
        LocalAppShapeTokens provides appShapeTokensFor(themeStyle),
        LocalThemeStyle provides themeStyle,
        LocalIsDarkTheme provides darkTheme
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            shapes = appShapesFor(themeStyle),
            content = content
        )
    }
}
