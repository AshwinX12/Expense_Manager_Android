package com.expensemanager.app.ui.theme

import androidx.compose.ui.graphics.Color
import com.expensemanager.app.data.db.entity.ThemeStyle

// ============================================================
// Classic theme (original default look)
// ============================================================

// Primary palette — muted indigo accent
val PrimaryLight = Color(0xFF6C63FF)
val PrimaryDark = Color(0xFF8B83FF)
val PrimaryContainer = Color(0xFFE8E6FF)
val OnPrimaryContainer = Color(0xFF1A1640)

// Surface colors
val SurfaceLight = Color(0xFFFAFAFC)
val SurfaceDark = Color(0xFF121218)
val SurfaceVariantLight = Color(0xFFF0F0F5)
val SurfaceVariantDark = Color(0xFF1E1E2A)
val CardLight = Color(0xFFFFFFFF)
val CardDark = Color(0xFF1A1A26)

// Background
val BackgroundLight = Color(0xFFF5F5FA)
val BackgroundDark = Color(0xFF0D0D14)

// Text
val TextPrimaryLight = Color(0xFF1A1A2E)
val TextPrimaryDark = Color(0xFFECECF0)
val TextSecondaryLight = Color(0xFF666680)
val TextSecondaryDark = Color(0xFF9999AA)

// Semantic colors — budget status
val BudgetGood = Color(0xFF4CAF50)      // Under budget (green)
val BudgetWarning = Color(0xFFFFA726)   // Approaching limit (amber)
val BudgetDanger = Color(0xFFE94560)    // Over budget (red)

// Income / Expense accents
val IncomeGreen = Color(0xFF00C853)
val ExpenseRed = Color(0xFFE94560)

// Category palette — consistent across all charts (Classic theme)
val CategoryColorsClassic = listOf(
    Color(0xFFFF6B6B), // Food & Dining
    Color(0xFF4ECDC4), // Transport
    Color(0xFF45B7D1), // Shopping
    Color(0xFF96CEB4), // Bills & Utilities
    Color(0xFFFFEAA7), // Entertainment
    Color(0xFFDDA0DD), // Health
    Color(0xFF98D8C8), // Education
    Color(0xFFF7DC6F), // Groceries
    Color(0xFFBB8FCE), // Rent
    Color(0xFF82E0AA), // Salary
    Color(0xFF85C1E9), // Freelance
    Color(0xFFAEB6BF), // Other
    Color(0xFFF1948A), // Extra 1
    Color(0xFF73C6B6), // Extra 2
    Color(0xFFF0B27A), // Extra 3
    Color(0xFF7FB3D8), // Extra 4
)

// Backwards-compatible alias used by call sites written before multi-theme support existed
val CategoryColors get() = CategoryColorsClassic

fun getCategoryColor(index: Int): Color = CategoryColorsClassic[index % CategoryColorsClassic.size]

fun String.toComposeColor(): Color {
    return try {
        Color(android.graphics.Color.parseColor(this))
    } catch (e: Exception) {
        CategoryColorsClassic[0]
    }
}

// ============================================================
// Theme A: Warm Paper / Stationery
// ============================================================

object WarmPaperColors {
    // Light
    val BackgroundLight = Color(0xFFFBF1E4)
    val SurfaceLight = Color(0xFFFFFFFF)
    val SurfaceAltLight = Color(0xFFEFE0C8)
    val TextPrimaryLight = Color(0xFF5C4630)
    val TextSecondaryLight = Color(0xFFB08968)
    val AccentLight = Color(0xFFC98A4B)
    val NeutralLight = Color(0xFF9A8F7E)
    val DividerLight = Color(0xFFEAD9BE)

    // Dark — fully neutral gray, zero hue tint. Warmth in dark mode (even at low
    // saturation) on backgrounds/text reads as a "reading mode"/night-shift color-cast
    // filter rather than a design choice, since text/surfaces cover almost the entire
    // screen. So warmth here lives ONLY in the accent color — everything else is plain gray.
    val BackgroundDark = Color(0xFF121212)
    val SurfaceDark = Color(0xFF1C1C1C)
    val SurfaceAltDark = Color(0xFF242424)
    val TextPrimaryDark = Color(0xFFEDEDED)
    val TextSecondaryDark = Color(0xFF9E9E9E)
    val AccentDark = Color(0xFFD1A671)
    // Neutral roles (secondary/tertiary/outline) — plain gray, not warm-tinted at all
    val NeutralDark = Color(0xFF828282)
    val DividerDark = Color(0xFF2E2E2E)

    // Semantic
    val StatusGood = Color(0xFF4E7C3E)
    val StatusWarning = Color(0xFFC98A4B)
    val StatusOver = Color(0xFFB5533C)

    // Category palette — muted, low-saturation earth tones only
    val CategoryPalette = listOf(
        Color(0xFFF6D9C4), // peach
        Color(0xFFD9E8D3), // sage
        Color(0xFFF0DDF0), // lilac
        Color(0xFFD8C9F6), // lavender
        Color(0xFFE8D9B8), // sand
        Color(0xFFC9E0D9), // seafoam
    )
}

// ============================================================
// Theme B: Editorial / Print-inspired
// ============================================================

object EditorialColors {
    // Light — warm beige paper, paired with a slate-ink accent. The original ochre accent
    // was what made this read as yellow (it filled the balance card and FAB), so the page
    // can stay warm as long as the large accent surfaces stay cool.
    val BackgroundLight = Color(0xFFF7F2E7)
    val TextPrimaryLight = Color(0xFF262420)
    val TextSecondaryLight = Color(0xFF5E594F)
    // Deep espresso ink — the dark end of the beige paper family, so large accent surfaces
    // (balance card, FAB) sit with the page instead of contrasting against it. Kept very
    // dark and low-chroma so it reads as ink rather than the ochre that looked yellow.
    val AccentLight = Color(0xFF3B322A)
    val NeutralLight = Color(0xFF837D70)
    val RuleLight = Color(0xFFE0D9C9)

    // Dark — fully neutral gray base; accent is a muted slate, not gold
    val BackgroundDark = Color(0xFF161616)
    val TextPrimaryDark = Color(0xFFE9E9E9)
    val TextSecondaryDark = Color(0xFF999999)
    val AccentDark = Color(0xFFA9BDD6)
    val NeutralDark = Color(0xFF7A7A7A)
    val RuleDark = Color(0xFF2E2E2E)

    // Semantic
    val StatusGood = Color(0xFF4A6B52)
    val StatusWarning = Color(0xFF8A6A3A)
    val StatusOver = Color(0xFF8A3E3E)

    // Category palette — restrained, ink-like editorial tones
    val CategoryPalette = listOf(
        Color(0xFF8A6A3A), // ochre
        Color(0xFF4A6B52), // forest green
        Color(0xFF8A3E3E), // rust/brick
        Color(0xFF3E5A6B), // slate blue
        Color(0xFF6B5A3E), // olive
        Color(0xFF5A4A6B), // muted plum
    )
}

// ============================================================
// Theme C: Memphis Geometric
// ============================================================

object MemphisColors {
    // Light
    val BackgroundLight = Color(0xFFF0EDE4)
    val SurfaceLight = Color(0xFFFFFFFF)
    val TextPrimaryLight = Color(0xFF222222)
    val TextSecondaryLight = Color(0xFF8A8A8A)
    val AccentLight = Color(0xFFEF476F)

    // Dark
    val BackgroundDark = Color(0xFF1A1A22)
    val SurfaceDark = Color(0xFF24242E)
    val TextPrimaryDark = Color(0xFFF2F0EA)
    val TextSecondaryDark = Color(0xFF9A98A8)
    val AccentDark = Color(0xFFFF6B8B)

    val DividerLight = Color(0xFFE2DED2)
    val DividerDark = Color(0xFF32323E)

    // Decorative background shapes — floating circles/triangles behind content
    val DecorativeYellow = Color(0xFFFFD166)
    val DecorativeTeal = Color(0xFF06D6A0)
    val DecorativeBlue = Color(0xFF118AB2)

    // Semantic
    val StatusGood = Color(0xFF06D6A0)
    val StatusWarning = Color(0xFFFFD166)
    val StatusOver = Color(0xFFEF476F)

    // Category palette — bold, flat, fully saturated (the one theme where this is intentional)
    val CategoryPalette = listOf(
        Color(0xFF06D6A0), // teal-green
        Color(0xFF118AB2), // blue
        Color(0xFFFFD166), // yellow
        Color(0xFFEF476F), // coral
        Color(0xFF7B61FF), // violet
        Color(0xFFFF9F5A), // orange
    )
}

// ============================================================
// Theme D: Terminal
// ============================================================

object TerminalColors {
    // Dark (default for this theme)
    val BackgroundDark = Color(0xFF0D0F10)
    val SurfaceDark = Color(0xFF16181A)
    val TextPrimaryDark = Color(0xFFD8DCD6)
    val TextSecondaryDark = Color(0xFF7A8078)
    val AccentDark = Color(0xFF8FE388)
    val WarningDark = Color(0xFFE8B94A)
    val ErrorDark = Color(0xFFE8837A)
    val RuleDark = Color(0xFF26292A)

    // Light
    val BackgroundLight = Color(0xFFF4F5F3)
    val SurfaceLight = Color(0xFFFFFFFF)
    val TextPrimaryLight = Color(0xFF1A1C1B)
    val TextSecondaryLight = Color(0xFF6B706C)
    val AccentLight = Color(0xFF2E8B3E)
    val WarningLight = Color(0xFFB8862E)
    val ErrorLight = Color(0xFFB8483C)
    val RuleLight = Color(0xFFDDE0DC)

    // Category palette — desaturated, terminal/ANSI-adjacent, used sparingly as indicator dots
    val CategoryPalette = listOf(
        Color(0xFF8FE388), // green
        Color(0xFF7AB8E8), // blue
        Color(0xFFE8B94A), // amber
        Color(0xFFE8837A), // red
        Color(0xFFC79FE8), // violet
        Color(0xFF7AD9C9), // cyan
    )
}

// ============================================================
// Theme F: Aurora — soft gradient blobs behind frosted glass cards
// ============================================================
// Design intent: a modern fintech look (Cash App / Revolut register) that none of the
// other five themes cover. One indigo-violet accent does the CTA work; income/expense get
// soft tinted chips instead of hard red/green. surfaceTint stays off (see Warm Paper's
// history) — the ambient color here comes from the deliberate blob backdrop, not an
// automatic elevation tint.

object AuroraColors {
    // Light
    val BackgroundLight = Color(0xFFF4F2FB)
    val SurfaceLight = Color(0xFFFFFFFF)
    val SurfaceAltLight = Color(0xFFEDE9F7)
    val TextPrimaryLight = Color(0xFF1F1B2E)
    val TextSecondaryLight = Color(0xFF6B6580)
    val AccentLight = Color(0xFF6D5DFC)
    val NeutralLight = Color(0xFF8A84A0)
    val DividerLight = Color(0xFFE3DFF2)

    // Dark
    val BackgroundDark = Color(0xFF0F0E17)
    val SurfaceDark = Color(0xFF181622)
    val SurfaceAltDark = Color(0xFF201E2E)
    val TextPrimaryDark = Color(0xFFEDEBFA)
    val TextSecondaryDark = Color(0xFF9B96B3)
    val AccentDark = Color(0xFF8B7CFF)
    val NeutralDark = Color(0xFF716B85)
    val DividerDark = Color(0xFF2A2838)

    // Semantic
    val StatusGood = Color(0xFF2C8C74)
    val StatusWarning = Color(0xFFC98A3D)
    val StatusOver = Color(0xFFB4467A)

    // Ambient backdrop blobs (background decoration only, never text)
    val GlowViolet = Color(0xFF9B8CF0)
    val GlowTeal = Color(0xFF7FD8C9)
    val GlowPink = Color(0xFFF5B8D8)

    // Category palette — soft pastel, distinct from every other theme's palette
    val CategoryPalette = listOf(
        Color(0xFF9B8CF0), // violet
        Color(0xFF7FD8C9), // teal
        Color(0xFFF5B8D8), // pink
        Color(0xFF7FA8F0), // sky blue
        Color(0xFFF0C77F), // amber
        Color(0xFFC79FE8), // plum
    )
}

/** Returns the category color palette for the given theme style — used when assigning colors to new categories. */
fun categoryPaletteFor(themeStyle: ThemeStyle): List<Color> = when (themeStyle) {
    ThemeStyle.CLASSIC -> CategoryColorsClassic
    ThemeStyle.WARM_PAPER -> WarmPaperColors.CategoryPalette
    ThemeStyle.EDITORIAL -> EditorialColors.CategoryPalette
    ThemeStyle.MEMPHIS -> MemphisColors.CategoryPalette
    ThemeStyle.TERMINAL -> TerminalColors.CategoryPalette
    ThemeStyle.AURORA -> AuroraColors.CategoryPalette
}
