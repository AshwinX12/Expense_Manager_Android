# UI Theme Specification — "Warm Paper" & "Editorial Print"

Two selectable visual themes for the expense manager web app. Both must be implemented as swappable theme token sets (CSS custom properties / Tailwind theme extension) sitting on top of the same component structure and layout defined in the base UI/UX spec — only color, type, spacing texture, and decorative treatment change between themes.

---

# Theme A: Warm Paper / Stationery

## Design intent
The app should feel like a well-made personal notebook or planner — approachable, tactile, calm. It should never look clinical or corporate. Every screen should feel like something the user would enjoy opening multiple times a day, not just tolerate.

## Color palette

**Light mode**
- Background (base): `#FBF1E4` (warm cream)
- Surface (cards): `#FFFFFF`
- Surface alt (secondary panels, e.g. budget bar background): `#EFE0C8` (soft tan)
- Primary text: `#5C4630` (warm brown, not black)
- Secondary/muted text: `#B08968` (dusty tan)
- Accent (primary actions, active states, links): `#C98A4B` (warm terracotta/amber)
- Divider: `#EAD9BE` (dashed, not solid — see "line treatment" below)

**Dark mode**
- Background: `#241C14` (warm near-black, brown-tinted, never pure `#000`)
- Surface (cards): `#2E251A`
- Surface alt: `#3A2E20`
- Primary text: `#F3E9D8`
- Secondary/muted text: `#B8A186`
- Accent: `#E0A868` (lighter warm amber for contrast against dark)
- Divider: `#4A3C2A`, dashed

**Category palette** (assigned consistently per category across every chart/screen):
`#F6D9C4` (peach), `#D9E8D3` (sage), `#F0DDF0` (lilac), `#D8C9F6` (lavender), `#E8D9B8` (sand), `#C9E0D9` (seafoam) — muted, low-saturation earth tones only. Never default chart-library blues/purples/reds.

**Semantic status colors** (used consistently for budget states across both light/dark):
- Under budget: `#4E7C3E` (moss green)
- Approaching limit: `#C98A4B` (same as accent, doubling as amber warning)
- Over budget: `#B5533C` (muted brick red — never a bright/saturated red)

## Typography
- Font: **Nunito** (rounded sans, weights 400/600/700) for all UI text — no system-ui/Inter default
- Monetary amounts: Nunito 700, large sizes (32–44px for hero balance figures), never a mono/tabular font here — the roundness is part of the "warm" feel
- Body/metadata text: Nunito 400, muted color, smaller size (12–13px)
- No italics, no serif anywhere in this theme

## Shape & texture
- Corner radius: consistently large and soft — 16–20px on all cards, chips, and buttons (never sharp corners, never a mix of radii on the same screen)
- No drop shadows. Depth communicated only through the surface/surface-alt color layering (white cards on cream background), not shadow elevation
- Optional subtle background texture: a very faint paper-grain noise overlay (≤3% opacity) on the base background only — must never appear on interactive elements or reduce text contrast
- Dividers between list rows: dashed, 1px, using the divider color — never solid hairlines (that's the Editorial theme's language, not this one)

## Iconography
- Rounded, slightly hand-drawn-adjacent icon style (not a generic geometric line-icon set like Feather/Heroicons used unmodified)
- Category icons rendered inside soft-colored circular or rounded-square chips using the category palette above

## Component treatment
- **Buttons (primary):** solid accent-color fill, white text, 16px+ radius, no shadow, subtle scale/opacity change on press (no ripple effects)
- **Cards:** white surface, 16–20px radius, no border, no shadow, generous internal padding (16–20px)
- **Progress bars (budget):** rounded pill track in surface-alt color, filled with the appropriate semantic status color, also rounded — never a hard-edged rectangular bar
- **Quick-add chips:** rounded-square, colored per category palette, icon + label stacked vertically
- **Input fields:** soft rounded rectangle, subtle surface-alt fill instead of a hard outline border

## Per-screen notes
- **Home:** balance card should feel like the "cover page" — most whitespace, most prominent card
- **Add:** numeric keypad keys use the rounded-corner button treatment; category chips use the circular/rounded-square icon chip style
- **Reports/charts:** donut and bar charts use the muted category palette; avoid any bright/saturated default chart-library colors leaking through

---

# Theme B: Editorial / Print-inspired

## Design intent
The app should feel like a page from a well-art-directed financial or lifestyle magazine — confident, quiet, typographically driven. Structure and rules (literal ruled lines) create hierarchy instead of color or shadow. This theme should feel more restrained and "grown-up" than Warm Paper.

## Color palette

**Light mode**
- Background: `#FBF7EF` (off-white/cream, cooler and lighter than Warm Paper's cream)
- Surface: same as background — this theme intentionally avoids differentiated "cards"; sections are separated by rules, not color blocks
- Primary text: `#2B2620` (near-black, warm undertone)
- Secondary/muted text: `#5A5142`
- Accent (primary actions, links, active nav): `#8A6A3A` (muted ochre/bronze)
- Rule/divider: `#D8CFBB` (hairline, solid — not dashed)

**Dark mode**
- Background: `#1C1A16`
- Surface: same as background
- Primary text: `#EDE7D8`
- Secondary/muted text: `#A79C86`
- Accent: `#C9A868` (brighter ochre for dark-mode contrast)
- Rule/divider: `#3A352C`

**Category palette** (restrained, editorial — like a print magazine's spot-color system):
`#8A6A3A` (ochre), `#4A6B52` (forest green), `#8A3E3E` (rust/brick), `#3E5A6B` (slate blue), `#6B5A3E` (olive), `#5A4A6B` (muted plum) — all desaturated, ink-like tones. No pastels, no neon, no default chart blues.

**Semantic status colors:**
- Under budget: `#4A6B52` (forest green, from category palette)
- Approaching limit: `#8A6A3A` (ochre)
- Over budget: `#8A3E3E` (brick red)

## Typography — this is the defining feature of the theme
- **Headline/display font (serif):** Source Serif 4 (or Source Serif Pro), weight 600 — used ONLY for: the hero balance figure, section headings ("Recent entries," "Budget progress," etc.), and screen titles
- **Body/UI font (sans):** Inter or a similar grotesque, weight 400/500 — used for all metadata, labels, list rows, buttons, and form inputs
- This pairing (serif headline + sans body) must be maintained everywhere — never let the serif bleed into body text or the sans into headings
- Balance figures: serif, 40–46px, tight letter-spacing
- Section labels (e.g. "BALANCE — JULY"): sans, all-caps, 10–11px, wide letter-spacing (1.5–2px), muted color — functions like a magazine kicker/eyebrow

## Shape & texture
- **No rounded corners on structural elements.** Square corners throughout (0–2px radius max) for cards, buttons, and dividers
- **No drop shadows, no filled "card" backgrounds.** Hierarchy comes entirely from typography scale, whitespace, and 1px hairline rules
- Section dividers: solid 1px hairline in the rule color, full-width, with generous vertical padding above/below (16–24px) — this replaces the concept of a "card" almost entirely
- Progress bars: a flat 1–2px line (not a pill/rounded bar) with a colored segment overlaid to indicate fill — reads as a "rule with a marker," not a typical progress bar widget

## Iconography
- Minimal to none. Prefer typographic/textual labels over icons wherever possible (e.g. category names as text rather than icon chips)
- Where an icon is unavoidable (e.g. category picker on Add screen), use a single-weight, thin-stroke line icon set, monochrome (colored via the category's assigned ink tone, not a bright fill)

## Component treatment
- **Buttons (primary):** text-forward, minimal — either a solid accent-color rectangle with white serif-adjacent label, or an underlined text-button style for secondary actions; no rounded pill buttons
- **Lists:** each row separated by a hairline rule (not a card), amount right-aligned, name left-aligned, consistent baseline grid
- **Quick-add / category chips (Add screen):** rendered as small bordered rectangles (1px solid border, square corners) with the category name as text, not stacked icon+label
- **Input fields:** underline-style (bottom border only, no full box), consistent with the "rules, not boxes" language of the theme

## Per-screen notes
- **Home:** treat like a magazine front page — masthead-style small header, large serif balance figure as the "headline," everything below organized by rule-separated sections rather than stacked cards
- **Add:** numeric keypad keys should be square, thin-bordered, serif numerals on the keys themselves (this is one of the few places serif appears outside headings, since the keypad numerals are effectively a "display" element)
- **Reports/charts:** use the restrained ink-tone category palette; chart backgrounds transparent (no chart "card" background) so charts sit directly on the page background, consistent with the "no boxes" philosophy

---

## Shared implementation notes (both themes)

- Both themes must fully support light/dark variants using the same token names (`--bg`, `--surface`, `--text-primary`, `--text-secondary`, `--accent`, `--divider`, `--status-good`, `--status-warning`, `--status-over`, plus `--category-1` through `--category-6`) so switching themes is a token-swap, not a component rewrite
- Category colors must map to the same category **consistently** across every chart type and screen within a theme, but the actual color values differ between the two themes (per palettes above) — do not reuse Warm Paper's category colors in Editorial or vice versa
- Neither theme should use: purple-to-blue gradients, uniform 12px "default" corner radius copied from one to the other, ambient soft drop shadows, or an unmodified default icon set — these are the exact tells to avoid per the original design brief
- Theme selection (Warm Paper vs. Editorial, plus light/dark within each) should live in `settings` and be applied at the root via a `data-theme` attribute or CSS class, per the existing theme-token architecture in the build spec
