package com.expensemanager.app.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.expensemanager.app.data.db.entity.ThemeStyle

val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(24.dp)
)

// Warm Paper: consistently large, soft radii everywhere
val WarmPaperShapes = Shapes(
    extraSmall = RoundedCornerShape(12.dp),
    small = RoundedCornerShape(14.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(20.dp),
    extraLarge = RoundedCornerShape(24.dp)
)

// Editorial: square corners throughout — hierarchy comes from rules, not shape
val EditorialShapes = Shapes(
    extraSmall = RoundedCornerShape(0.dp),
    small = RoundedCornerShape(0.dp),
    medium = RoundedCornerShape(0.dp),
    large = RoundedCornerShape(0.dp),
    extraLarge = RoundedCornerShape(0.dp)
)

// Memphis: large, consistent rounding, paired with bold flat color
val MemphisShapes = Shapes(
    extraSmall = RoundedCornerShape(10.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(18.dp),
    extraLarge = RoundedCornerShape(24.dp)
)

// Terminal: sharp rectangular panels throughout
val TerminalShapes = Shapes(
    extraSmall = RoundedCornerShape(0.dp),
    small = RoundedCornerShape(0.dp),
    medium = RoundedCornerShape(0.dp),
    large = RoundedCornerShape(0.dp),
    extraLarge = RoundedCornerShape(0.dp)
)

// Aurora: large, soft radii — glass cards read as pillowy over the gradient backdrop
val AuroraShapes = Shapes(
    extraSmall = RoundedCornerShape(12.dp),
    small = RoundedCornerShape(16.dp),
    medium = RoundedCornerShape(18.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

fun appShapesFor(themeStyle: ThemeStyle): Shapes = when (themeStyle) {
    ThemeStyle.CLASSIC -> AppShapes
    ThemeStyle.WARM_PAPER -> WarmPaperShapes
    ThemeStyle.EDITORIAL -> EditorialShapes
    ThemeStyle.MEMPHIS -> MemphisShapes
    ThemeStyle.TERMINAL -> TerminalShapes
    ThemeStyle.AURORA -> AuroraShapes
}

/** App-specific shape tokens beyond Material's default 5-step scale, swapped per theme. */
data class AppShapeTokens(
    val card: Shape,
    val chip: Shape,
    val button: Shape,
    val bottomSheet: Shape
)

private val ClassicShapeTokens = AppShapeTokens(
    card = RoundedCornerShape(16.dp),
    chip = RoundedCornerShape(20.dp),
    button = RoundedCornerShape(12.dp),
    bottomSheet = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
)

private val WarmPaperShapeTokens = AppShapeTokens(
    card = RoundedCornerShape(20.dp),
    chip = RoundedCornerShape(16.dp), // rounded-square chip, not a pill
    button = RoundedCornerShape(18.dp),
    bottomSheet = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
)

private val EditorialShapeTokens = AppShapeTokens(
    card = RoundedCornerShape(0.dp),
    chip = RoundedCornerShape(0.dp),
    button = RoundedCornerShape(0.dp),
    bottomSheet = RoundedCornerShape(0.dp)
)

private val MemphisShapeTokens = AppShapeTokens(
    card = RoundedCornerShape(18.dp),
    chip = RoundedCornerShape(16.dp),
    button = RoundedCornerShape(16.dp),
    bottomSheet = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
)

private val TerminalShapeTokens = AppShapeTokens(
    card = RoundedCornerShape(0.dp),
    chip = RoundedCornerShape(0.dp),
    button = RoundedCornerShape(0.dp),
    bottomSheet = RoundedCornerShape(0.dp)
)

private val AuroraShapeTokens = AppShapeTokens(
    card = RoundedCornerShape(28.dp),
    chip = RoundedCornerShape(16.dp),
    button = RoundedCornerShape(18.dp),
    bottomSheet = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
)

fun appShapeTokensFor(themeStyle: ThemeStyle): AppShapeTokens = when (themeStyle) {
    ThemeStyle.CLASSIC -> ClassicShapeTokens
    ThemeStyle.WARM_PAPER -> WarmPaperShapeTokens
    ThemeStyle.EDITORIAL -> EditorialShapeTokens
    ThemeStyle.MEMPHIS -> MemphisShapeTokens
    ThemeStyle.TERMINAL -> TerminalShapeTokens
    ThemeStyle.AURORA -> AuroraShapeTokens
}

val LocalAppShapeTokens = staticCompositionLocalOf { ClassicShapeTokens }
val LocalThemeStyle = staticCompositionLocalOf { ThemeStyle.CLASSIC }

// Backwards-compatible top-level accessors — existing screens reference these directly,
// so they resolve from the current theme's tokens instead of a fixed shape.
val CardShape: Shape
    @Composable @ReadOnlyComposable get() = LocalAppShapeTokens.current.card

val ChipShape: Shape
    @Composable @ReadOnlyComposable get() = LocalAppShapeTokens.current.chip

val ButtonShape: Shape
    @Composable @ReadOnlyComposable get() = LocalAppShapeTokens.current.button

val BottomSheetShape: Shape
    @Composable @ReadOnlyComposable get() = LocalAppShapeTokens.current.bottomSheet
