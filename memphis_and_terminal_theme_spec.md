# UI Theme Specification — "Memphis Geometric" & "Terminal"

Two more selectable visual themes for the expense manager web app, built on the same token architecture as the Warm Paper / Editorial themes: same component structure and layout from the base UI/UX spec, only color, type, shape, and decorative treatment change per theme. Theme selection lives in `settings` and applies via a `data-theme` attribute driving CSS custom properties.

---

# Theme C: Memphis Geometric

## Design intent
The app should feel fun, energetic, and a little irreverent — closer to a lifestyle/habit app than a serious finance tool, while still keeping numbers fully legible and primary. This theme is for a user who wants opening the app to feel good, not just functional. Confidence comes from bold flat color and playful shape, not from restraint.

## Color palette

**Light mode**
- Background: `#F0EDE4` (warm light grey, slightly warm so flat colors pop without looking cold)
- Surface (cards): `#FFFFFF`
- Primary text: `#222222`
- Secondary/muted text: `#8A8A8A`
- Accent (primary actions): `#EF476F` (bright coral/pink)
- Decorative shape colors (background accents only, never text): `#FFD166` (yellow), `#06D6A0` (teal-green), `#118AB2` (blue) — used as floating circles/triangles behind or beside cards, at reduced opacity (30–40%) so they never interfere with text contrast

**Dark mode**
- Background: `#1A1A22`
- Surface: `#24242E`
- Primary text: `#F2F0EA`
- Secondary/muted text: `#9A98A8`
- Accent: `#FF6B8B` (brightened coral for dark contrast)
- Decorative shape colors: same hues as light mode but rendered at lower opacity (15–20%) against the dark background so they read as subtle glow rather than clutter

**Category palette** (bold, flat, fully saturated — this is the one theme where saturation is intentional):
`#06D6A0` (teal-green), `#118AB2` (blue), `#FFD166` (yellow), `#EF476F` (coral), `#7B61FF` (violet), `#FF9F5A` (orange) — always used as solid flat fills, never gradients, never muted/pastel versions mixed in with the full-saturation set.

**Semantic status colors:**
- Under budget: `#06D6A0` (teal-green)
- Approaching limit: `#FFD166` (yellow) — text on this color must be dark (`#222`) for contrast
- Over budget: `#EF476F` (coral-red)

## Typography
- Font: **Poppins**, weights 500/700 — geometric, rounded-adjacent sans, distinct from Warm Paper's Nunito (softer/rounder) and from any default system font
- Monetary amounts: Poppins 700, 32–40px
- Body/metadata: Poppins 500, smaller size, muted color
- Avoid all-caps labels in this theme (that's the Editorial/Terminal language) — use sentence case throughout for a friendlier tone

## Shape & texture
- Corner radius: large and consistent, 16–18px on cards and chips — similar generosity to Warm Paper, but paired with bold color instead of muted tone
- **Decorative geometric shapes required:** every screen should include 1–3 subtle background shapes (circles, triangles, quarter-circles) in the decorative accent colors, positioned at screen edges/corners, layered behind content — these are load-bearing for the theme's identity and should not be treated as optional flourish
- No drop shadows — flat design throughout; depth comes from color contrast and the decorative shapes, not elevation
- Category chips and status badges: solid flat color fill (never a colored border on white), white or near-black text depending on the fill's lightness for contrast

## Iconography
- Simple, bold, single-color icons (filled style, not thin-line) — should read clearly even at small size against the theme's saturated color chips
- Icons sit on solid category-color circular chips (white or dark icon on the color, per contrast rules above)

## Component treatment
- **Buttons (primary):** solid accent-color (`#EF476F`) fill, white bold text, 16px+ radius, no shadow; pressed state = brief scale-down, not a color-darken (keep the flat-color read consistent)
- **Cards:** white surface (or dark-mode surface), rounded 16–18px, no border, no shadow; may have a single decorative shape peeking from behind a corner
- **Progress bars:** rounded pill, filled with the relevant semantic status color at full saturation; track in a light neutral grey, never a muted/dark grey (keep it bright and legible)
- **Quick-add chips:** solid color rounded-square per category, white bold label
- **Input fields:** rounded rectangle, light grey fill, no visible border until focused (then accent-colored 2px border)

## Per-screen notes
- **Home:** balance card in white/surface color with 1–2 decorative shapes bleeding from behind it; budget progress bar should be one of the most colorful elements on the screen
- **Add:** numeric keypad keys can each be a slightly different flat color OR uniform white with a bold accent "Save" button — avoid keys in six different saturated colors simultaneously, as that undermines legibility; reserve full-color variety for category chips, not the keypad
- **Reports/charts:** this theme is the natural home for the most colorful chart rendering — pie/donut and bar charts should confidently use the full saturated category palette; this is the one theme where a "busy" chart is acceptable and even desirable

---

# Theme D: Terminal

## Design intent
The app should feel like a precision tool built by and for someone who thinks in data — closer to a trading terminal, a well-designed CLI, or a developer dashboard than a consumer finance app. Restraint and monospace alignment communicate credibility; color is used sparingly and functionally, never decoratively.

## Color palette

**Dark mode (primary/default for this theme — this theme should default to dark even before considering the user's OS preference, though a light variant must still exist per the light/dark toggle requirement)**
- Background: `#0D0F10` (near-black, very slightly warm)
- Surface (cards/panels): `#16181A`
- Primary text: `#D8DCD6` (light grey-green, not pure white — reduces eye strain, reads as "terminal")
- Secondary/muted text: `#7A8078`
- Accent (primary actions, positive values): `#8FE388` (terminal green)
- Warning accent: `#E8B94A` (amber)
- Negative/error accent: `#E8837A` (muted red)
- Rule/divider: `#26292A`

**Light mode**
- Background: `#F4F5F3`
- Surface: `#FFFFFF`
- Primary text: `#1A1C1B`
- Secondary/muted text: `#6B706C`
- Accent: `#2E8B3E` (darker green for light-background contrast)
- Warning accent: `#B8862E`
- Negative/error accent: `#B8483C`
- Rule/divider: `#DDE0DC`

**Category palette** (desaturated, terminal-appropriate — think ANSI-adjacent but not literal ANSI colors):
`#8FE388` (green), `#7AB8E8` (blue), `#E8B94A` (amber), `#E8837A` (red), `#C79FE8` (violet), `#7AD9C9` (cyan) — used sparingly, typically as small indicator dots or single-color category tags, never as large color fields

**Semantic status colors:**
- Under budget: `#8FE388` (green)
- Approaching limit: `#E8B94A` (amber)
- Over budget: `#E8837A` (red)

## Typography
- Font: **JetBrains Mono** (or IBM Plex Mono), weights 400/500/700 — monospace throughout, including body text, not just numbers. This is the one theme where monospace is used everywhere, not just for figures
- Monetary amounts: JetBrains Mono 700, right-aligned wherever they appear in a list/table context
- Labels: lowercase, terse (e.g. `balance`, `budget_status`, `recent:`) rather than title-case UI copy — evokes CLI output/variable names, but must remain genuinely readable, not cryptic
- No letter-spacing tricks needed for hierarchy — size and color weight do that work instead

## Shape & texture
- Corner radius: **none, or minimal (0–2px)** — sharp rectangular panels throughout, consistent with a terminal/data-tool aesthetic
- No drop shadows, no gradients. Flat single-color fills or thin 1px borders only
- Panels defined by either a thin 1px border in the divider color, or a subtle surface-color fill against the background — pick one convention and use it consistently across all cards/sections
- Tables/lists: true tabular alignment — amounts right-aligned in a fixed-width column, labels left-aligned, using the monospace font's natural character grid rather than flex-justify approximations
- Progress bars: thin flat rectangular bar (4–6px tall), square ends, filled with the semantic status color — no rounding, no gradient fill

## Iconography
- Minimal to none. Prefer text/symbols over icons (e.g. `+` / `-` prefixes on amounts instead of income/expense icons, `>` as a bullet marker for list-like content)
- Where icons are unavoidable (e.g. bottom nav), use a single-weight, minimal geometric icon set at small size, colored with muted text color (not accent) when inactive, accent color when active

## Component treatment
- **Buttons (primary):** rectangular, flat accent-color fill or a bordered "outline button" style (1px border, transparent fill, accent-colored text) — no rounded corners; hover/press state = brightness shift, not shadow
- **Cards/panels:** surface-color fill or bordered rectangle, sharp corners, compact padding (tighter than the other three themes — this theme should read as more information-dense, consistent with Section 11's performance requirements at 10k+ transactions)
- **Quick-add chips:** small bordered rectangles with `>` prefix and monospace label (e.g. `> coffee ... ₹150`), not icon-based chips
- **Input fields:** bordered rectangle, sharp corners, monospace input text, blinking-cursor-style focus state if feasible

## Per-screen notes
- **Home:** balance rendered large in accent green, everything else in a tight vertical stack of labeled data rows rather than separated visual "cards" — the whole screen can read almost like a single console output block
- **Add:** numeric keypad keys should be sharp-cornered, monospace numerals, minimal-color (dark keys, light text, accent only on the "Save" action) — this is a deliberate contrast with Memphis's colorful keypad approach
- **Transactions list:** given the 10k-row performance requirement, this theme is the most natural fit for a dense, virtualized, table-like list rendering — lean into that rather than trying to make rows feel like cards
- **Reports/charts:** charts should be minimal-chrome — thin sparklines, single-color bars, no chart background fill, gridlines (if any) in the divider color at low opacity; avoid multi-color pie charts here, prefer a simple ranked bar list with color used only for the status indicator, not decoration

---

## Shared implementation notes (all four themes)

- All four themes (Warm Paper, Editorial, Memphis, Terminal) must share the same token names (`--bg`, `--surface`, `--text-primary`, `--text-secondary`, `--accent`, `--divider`, `--status-good`, `--status-warning`, `--status-over`, `--category-1` through `--category-6`) so theme switching is a token-swap at the root (`data-theme` attribute), never a component-level rewrite
- Each theme has its own light AND dark variant (Terminal defaults to dark but must still support light); do not assume "dark mode" only applies to Terminal
- No theme should borrow another theme's defining signature: Memphis's decorative floating shapes should never appear in Terminal; Terminal's monospace-everywhere should never appear in Memphis; corner radius conventions (sharp for Terminal, generous+rounded for Memphis) must not blend
- Category color-to-category mapping must stay consistent within a theme across every chart and screen, but the actual color values are theme-specific per the palettes above — do not reuse one theme's category palette in another
