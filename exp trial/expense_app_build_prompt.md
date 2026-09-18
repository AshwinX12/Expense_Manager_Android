# Android expense manager app — build specification

## Purpose

You are building a **personal expense and income management Android app** for a single user, single device. No multi-user accounts, no live cloud sync required. The core design principle is speed of daily use — the user will log expenses multiple times a day, so entry flow must be as frictionless as possible. All data is local-first, stored on-device, with export/import used only for backup or device migration (not live sync).

Build this as a native Android app (Kotlin, Jetpack Compose preferred) or a cross-platform app (Flutter) — choose whichever the agent's environment supports best, but prioritize offline-first local storage (e.g. Room database / SQLite) as the source of truth.

---

## 1. Expense & income entry

- Manual entry: amount, date, category, subcategory, payment method, account, note/description
- Quick-add shortcuts: user-configurable one-tap buttons for frequent/repeated expenses (e.g. "Coffee — ₹150 — Food")
- Bulk entry mode: add multiple transactions in one continuous session without returning to the home screen each time
- Edit and delete any existing entry
- Income tracking: separate transaction type for income (salary, freelance, gifts, refunds), distinct from expenses but shown in unified balance calculations

## 2. Organization & structuring

- Custom categories: unlimited, fully user-defined, not locked to a preset list
- Subcategories: nested under a parent category (e.g. Food → Groceries, Dining Out, Snacks)
- Tags: independent of categories, multiple tags per transaction, used for cross-cutting filtering (e.g. "business trip", "tax deductible", "reimbursable")
- Multiple accounts: bank accounts, cash, credit cards, digital wallets — each tracked separately with its own running balance, but viewable together in aggregate
- Multi-currency support: log transactions in different currencies; store a conversion rate (manual entry acceptable, no live FX API required) to normalize into a base currency for reporting
- Lending & borrowing tracker: record money lent to or borrowed from specific people, with a repayment status (pending / partially repaid / settled) and running balance per person
- Split expenses: divide a single transaction among multiple people, track each person's share and settlement status

## 3. Recurring & scheduled transactions

- Recurring expenses: auto-generate entries on a defined schedule (daily/weekly/monthly/custom) for fixed costs like rent, subscriptions, EMIs
- Scheduled/future-dated transactions: allow entries dated in the future, excluded from past-spend totals until their date arrives
- Bill reminders: general notification system for upcoming bills tied to a transaction or recurring rule
- Credit card bill reminders: a distinct reminder type tied to a due date, with configurable lead time (e.g. notify 3 days before)

## 4. Budgeting

- Category-wise budgets: a spending limit set per category per period (weekly/monthly)
- Overall budget: a single total spending cap across all categories for the period
- Budget alerts: local notification when spend crosses a configurable threshold (e.g. 80%) and again when exceeded
- Budget rollover: optional setting to carry unused budget into the next period instead of resetting to zero
- Goal-based budgeting: define a savings goal with a target amount and optional target date
- Transfer to goal: a dedicated transaction type that moves a specified amount from the general balance/budget into a named goal, tracked as its own ledger entry (not a regular expense or income)

## 5. Search, filter & retrieval

- Search by merchant/payee name, note text, or amount (exact or range)
- Filter by date range, category, subcategory, tag, account, payment method — filters must be combinable (AND logic across filter types)
- Sort by amount, date, or category (ascending/descending)
- Saved filters/views: let the user save a specific filter combination and re-apply it in one tap

## 6. Backup & data portability

- Data export: generate CSV and PDF exports of transactions for a selected date range, suitable for taxes or personal record-keeping
- Data import: import transactions from a CSV file (e.g. bank statement export), with a column-mapping step since formats vary
- Full data export/import for device migration: a single export function that serializes the entire local database — transactions, categories, tags, accounts, budgets, goals, recurring rules, settings — into one portable file. Importing this file on a fresh install must fully restore the app to its prior state with no data loss.
- Offline-first: the app must be fully functional with zero network connectivity for all core features (entry, editing, budgeting, viewing reports). Network is only ever used for optional export/import file handling (e.g. saving to device storage or sharing).

## 7. Security

- App lock: PIN and biometric (fingerprint/Face unlock via Android BiometricPrompt API)
- Local data encryption at rest (e.g. SQLCipher for the Room/SQLite database)
- No data leaves the device except via explicit user-triggered export

## 8. Usability & interface

- Visual style: flat, minimal, card-based UI. No heavy gradients or decorative shadows. Generous whitespace. See "UI/UX specification" section below for full detail.
- Dark mode with a light/dark theme toggle (and system-default option)
- No ads
- Home screen widget: Android widget showing current balance and/or today's spend at a glance
- Multi-language support: architect strings for localization (even if only English is shipped initially)
- Accessibility: support system font scaling, sufficient touch target sizes (minimum 44dp), adequate color contrast in both themes

## 9. Notifications & reminders

- Budget threshold alerts (per category and overall)
- Bill due date reminders (general and credit-card specific)
- Unusual spending alerts: flag a transaction that is significantly above the user's typical spend in that category (define "unusual" via a simple statistical threshold, e.g. more than 2x the trailing 3-month category average)
- Weekly/monthly summary notification: auto-generated recap of total spend, income, and budget status

## 10. Visualizations & reports

All of the following must be implemented as charts/visual components, not just tables:

- Category-wise breakdown — pie or donut chart
- Spending trend over time — line chart, switchable between daily/weekly/monthly granularity
- Income vs. expense comparison — bar chart by month
- Budget vs. actual spend — progress bars per category, color-coded (green = under budget, yellow = approaching limit, red = over budget)
- Account balance overview — summary cards or a stacked bar showing all accounts together
- Top spending categories — ranked bar chart or list, configurable top N
- Cash flow calendar/heatmap — calendar view color-shaded by daily spend intensity
- Net worth/savings trend — line chart tracking total balance or savings growth over time
- Lending/borrowing summary — table/list view of amounts owed to and by the user, per person
- Year-in-review report — an annual summary screen with key stats (total spent, top category, biggest month, savings rate, etc.)
- Custom date range reports — let the user pick any start/end date and regenerate all relevant charts for that window

## 11. Data & visualization behavior (technical requirements)

These govern how the app behaves internally, not user-facing features:

- **Live-updating visualizations**: every chart and report must recompute from the current dataset immediately after any transaction is added, edited, or deleted. No manual refresh action should ever be required.
- **Cross-visualization consistency**: a single data change must be reflected across every affected view (e.g. editing one transaction updates the category chart, the trend chart, the budget bar, and the account balance simultaneously) — do not let views go stale independently.
- **Update trigger**: recompute on transaction save/delete, not on every keystroke while typing. This is sufficient for personal-finance use and avoids unnecessary recomposition/render churn.
- **No cached/static snapshots**: charts must be derived from a live query against the local database (e.g. reactive Room Flow/LiveData queries feeding directly into Compose state), not pre-generated images or one-time computed values.
- **Single-device architecture**: do not build any live multi-device sync infrastructure (no backend server, no real-time sync engine). The only mechanism for moving data between devices is the full export/import feature described in Section 6.

---

## UI/UX specification

### Overall style
Card-based, minimal, single-focus screens. Avoid dense, cluttered dashboards. Generous whitespace. One primary action per screen. Numbers are the content — they should be large, bold, and legible; the finance-specific numbers take visual priority over decorative UI.

### Navigation
Bottom tab bar with 5 destinations:
1. **Home/Dashboard** — balance overview, budget progress, quick-add shortcuts, recent transactions
2. **Add** — large floating action button (FAB), opens the fast entry flow
3. **Transactions** — full searchable/filterable/sortable list
4. **Budgets/Goals** — budget progress per category, goals, transfer-to-goal action
5. **Reports** — all visualizations from Section 10, plus custom date range reports

### Home screen (top to bottom)
1. Current balance, large and bold, top of screen
2. This period's budget progress — a slim progress bar or ring, color-coded per Section 10 rules
3. Quick-add shortcuts — 3–4 tappable icon buttons for frequent expenses
4. Mini spend chart — small trend line or category donut, tappable to expand into full Reports tab
5. Recent transactions — last 5–10 entries with category icon, name, date, amount; "see all" link to Transactions tab

### Add-expense screen
- Large numeric keypad, calculator-style, as the dominant UI element (amount entry is the single most frequent action in the app)
- Category picker as a horizontally scrollable row of icon chips, not a dropdown — faster to tap, one thumb operable
- Account and date collapsed into a compact row by default
- Note field optional, low visual priority
- Primary "Save expense" button always visible without scrolling, reachable in the lower half of the screen for one-handed use

### Color & visual language
- Muted, neutral base palette; one consistent accent color for primary actions across the whole app
- Semantic colors for budget/spend status, applied consistently everywhere: green = under budget/good, yellow/amber = approaching limit, red = over budget
- Charts use a consistent, limited color palette per category so the same category always renders in the same color across every screen and chart type
- Support both light and dark themes fully; do not hardcode colors — use a theme token system

### Typography & density
- Large, bold numerals for all monetary amounts
- Secondary metadata (dates, notes, account names) in smaller, muted text
- Prefer list rows (icon + name + amount) over dense tables wherever the content allows it

---

## Non-functional requirements

- Local storage: Room (SQLite) or equivalent embedded database, encrypted at rest
- Performance: all list views and charts must remain responsive with at least 10,000 transactions in the database
- No third-party analytics or ad SDKs
- No network permission required for core functionality; network permission (if included at all) is scoped only to file export/share intents
- Target modern Android API levels with backward compatibility to a reasonably recent minimum SDK (agent to decide based on current Android distribution data at build time)

---

## Deliverable

A functioning Android application implementing all sections above, structured as a maintainable codebase (clear separation of data layer, business logic, and UI), with the Home, Add, Transactions, Budgets/Goals, and Reports screens fully implemented per the UI/UX specification.
