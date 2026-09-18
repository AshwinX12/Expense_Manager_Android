# Android Expense Manager App — Implementation Plan

## Overview

Build a full-featured, offline-first personal expense/income management Android app using **Kotlin + Jetpack Compose + Room (SQLCipher)**, following MVVM architecture with Hilt dependency injection. The app is local-first, single-user, single-device, with no cloud sync — data portability via export/import only.

---

## User Review Required

> [!IMPORTANT]
> **This is a very large application** with 11 feature sections, 10+ chart types, and dozens of screens. The full build will produce approximately **100+ Kotlin files** across data, domain, and UI layers. Building the entire app in one pass is the plan — please confirm you'd like to proceed with the full scope.

> [!IMPORTANT]
> **App package name**: I will use `com.expensemanager.app`. Let me know if you prefer a different package name.

> [!IMPORTANT]
> **Minimum SDK**: I will target **API 26 (Android 8.0)** as the minimum, with a target SDK of **API 36 (Android 16)** since that's what you're running. This covers ~97% of active Android devices.

> [!WARNING]
> **SQLCipher encryption**: The encryption passphrase will be generated at first launch and stored securely in the Android Keystore. This means if the user clears app data, the database becomes unrecoverable unless they have an export backup.

---

## Open Questions

> [!IMPORTANT]
> **Charting library**: I plan to use **Vico** (modern Compose-native charting library) for all charts. It supports line, bar, pie/donut, and is actively maintained. Alternative: MPAndroidChart (legacy, View-based). Do you have a preference?

> [!NOTE]
> **Home screen widget**: Android widgets require a separate `AppWidgetProvider` and XML layout. I will implement a basic widget showing today's spend and current balance. Full widget customization can be iterated on later.

---

## Architecture

```
app/src/main/java/com/expensemanager/app/
├── data/                          # Data Layer
│   ├── db/                        # Room database, entities, DAOs, type converters
│   │   ├── entity/                # All Room entities
│   │   ├── dao/                   # All DAO interfaces
│   │   ├── converter/             # Type converters (date, enum, etc.)
│   │   └── AppDatabase.kt        # Room database class
│   ├── repository/                # Repository implementations
│   └── export/                    # CSV/PDF export & import logic
├── domain/                        # Domain Layer
│   ├── model/                     # Domain models / UI models
│   └── usecase/                   # Business logic use cases
├── ui/                            # UI Layer (Jetpack Compose)
│   ├── theme/                     # Theme, colors, typography tokens
│   ├── components/                # Reusable composables
│   ├── navigation/                # NavHost, bottom bar, routes
│   ├── home/                      # Home/Dashboard screen
│   ├── add/                       # Add/Edit transaction screen
│   ├── transactions/              # Transaction list screen
│   ├── budgets/                   # Budgets & Goals screen
│   ├── reports/                   # Reports & Charts screen
│   ├── settings/                  # Settings screen
│   ├── accounts/                  # Account management
│   ├── categories/                # Category/subcategory management
│   ├── lending/                   # Lending/borrowing tracker
│   └── search/                    # Search & filter
├── di/                            # Hilt DI modules
├── notification/                  # NotificationManager, WorkManager schedulers
├── security/                      # Biometric auth, app lock, keystore
├── widget/                        # Home screen widget
└── util/                          # Extensions, helpers, constants
```

---

## Proposed Changes

### Phase 1: Project Setup & Foundation

#### [NEW] Gradle project configuration
- `build.gradle.kts` (project-level): AGP, Kotlin, Hilt, KSP plugins
- `app/build.gradle.kts`: All dependencies — Compose BOM, Room, Hilt, SQLCipher, Vico charts, Navigation Compose, Material 3, WorkManager, Biometric API, iText (PDF export), Apache Commons CSV
- `gradle/libs.versions.toml`: Version catalog

#### [NEW] `AppDatabase.kt`
- Room database with all entity classes, all DAOs
- SQLCipher `SupportFactory` integration
- Passphrase generated at first launch, stored in Android Keystore
- Type converters for `LocalDate`, `LocalDateTime`, `BigDecimal`, enums, list of strings

#### [NEW] `di/DatabaseModule.kt`, `di/RepositoryModule.kt`
- Hilt modules providing database, DAOs, repositories as singletons

---

### Phase 2: Data Layer — Entities & DAOs

#### [NEW] Entities (`data/db/entity/`)

| Entity | Key Fields |
|--------|-----------|
| `TransactionEntity` | id, amount, currency, conversionRate, type (EXPENSE/INCOME/TRANSFER_TO_GOAL), date, categoryId, subcategoryId, accountId, paymentMethod, note, merchantName, isRecurring, recurringRuleId, tags (JSON list), hasAttachments |
| `CategoryEntity` | id, name, iconName, colorHex, isDefault |
| `SubcategoryEntity` | id, name, parentCategoryId |
| `AccountEntity` | id, name, type (BANK/CASH/CREDIT_CARD/DIGITAL_WALLET), currency, initialBalance, iconName, colorHex |
| `TagEntity` | id, name, colorHex |
| `TransactionTagCrossRef` | transactionId, tagId (junction table) |
| `BudgetEntity` | id, categoryId (null = overall), amount, period (WEEKLY/MONTHLY), rolloverEnabled, rolloverAmount, startDate |
| `GoalEntity` | id, name, targetAmount, currentAmount, targetDate, iconName, colorHex, isCompleted |
| `RecurringRuleEntity` | id, templateTransactionId, frequency (DAILY/WEEKLY/MONTHLY/CUSTOM), interval, nextOccurrence, endDate, isActive |
| `ReminderEntity` | id, title, description, dueDate, leadTimeDays, type (BILL/CREDIT_CARD/CUSTOM), recurringRuleId, isActive |
| `LendingEntity` | id, personName, amount, type (LENT/BORROWED), date, note, status (PENDING/PARTIAL/SETTLED), repaidAmount |
| `SplitExpenseEntity` | id, transactionId, personName, shareAmount, isSettled |
| `QuickAddShortcutEntity` | id, label, amount, categoryId, accountId, iconName, sortOrder |
| `SavedFilterEntity` | id, name, filterJson (serialized filter criteria) |
| `CurrencyEntity` | code, name, symbol, conversionRateToBase |
| `AttachmentEntity` | id, transactionId, fileName, filePath, mimeType, thumbnailPath, createdAt |
| `SettingsEntity` | key, value (key-value store for app settings) |

#### [NEW] DAOs (`data/db/dao/`)
- `TransactionDao`: CRUD, filtered queries (by date range, category, account, tag, amount range, text search), aggregations (sum by category, sum by period, running balance per account)
- `AttachmentDao`: CRUD per transaction, list attachments for a transaction
- `CategoryDao`, `SubcategoryDao`: CRUD, list with transaction counts
- `AccountDao`: CRUD, balance calculations
- `TagDao`: CRUD
- `BudgetDao`: CRUD, budget vs actual spend queries
- `GoalDao`: CRUD, progress tracking
- `RecurringRuleDao`: CRUD, due rules query
- `ReminderDao`: CRUD, upcoming reminders query
- `LendingDao`: CRUD, balance per person
- `SplitExpenseDao`: CRUD, per-transaction splits
- `QuickAddShortcutDao`: CRUD, ordered list
- `SavedFilterDao`: CRUD
- `CurrencyDao`: CRUD
- All queries returning `Flow<List<T>>` for reactive UI

---

### Phase 3: Domain Layer

#### [NEW] Repositories (`data/repository/`)
- `TransactionRepository` — transaction CRUD, search, filter, aggregation
- `CategoryRepository` — category & subcategory management
- `AccountRepository` — account CRUD, balance management
- `BudgetRepository` — budget CRUD, rollover logic
- `GoalRepository` — goal CRUD, transfer-to-goal logic
- `RecurringTransactionRepository` — recurring rule management, auto-generation
- `ReminderRepository` — reminder scheduling
- `LendingRepository` — lending/borrowing CRUD
- `ExportImportRepository` — CSV/PDF generation, CSV import with column mapping, full DB export/import
- `AttachmentRepository` — save/delete images, manage file storage in app-internal directory
- `SettingsRepository` — app settings management

#### [NEW] Use Cases (`domain/usecase/`)
- `GetDashboardDataUseCase` — aggregates balance, budget progress, recent transactions
- `AddTransactionUseCase` — validates and saves, updates account balance
- `GetFilteredTransactionsUseCase` — applies combined filters
- `CalculateBudgetStatusUseCase` — computes spent vs budget per category
- `GenerateRecurringTransactionsUseCase` — creates due recurring entries
- `CheckUnusualSpendingUseCase` — compares transaction to trailing 3-month category average
- `ExportTransactionsUseCase` — CSV/PDF generation
- `ImportTransactionsUseCase` — CSV parsing with column mapping
- `FullBackupExportUseCase` / `FullBackupImportUseCase` — complete DB serialization

---

### Phase 4: UI — Theme & Components

#### [NEW] Theme (`ui/theme/`)
- `Color.kt` — light/dark palettes, semantic colors (green/yellow/red for budget status), category color palette
- `Type.kt` — typography scale using Inter/Roboto font family, large bold numerals
- `Theme.kt` — Material 3 dynamic theming with light/dark/system-default toggle
- `Shape.kt` — rounded card shapes
- `Dimens.kt` — spacing, padding constants, minimum 44dp touch targets

#### [NEW] Reusable Components (`ui/components/`)
- `AmountText` — large, bold, formatted currency display
- `TransactionListItem` — icon + name + category + amount row
- `CategoryChip` — icon chip for category selection
- `BudgetProgressBar` — color-coded (green/yellow/red) progress
- `NumericKeypad` — calculator-style amount entry
- `FilterBar` — combinable filter chips
- `ChartCard` — card wrapper for chart components
- `EmptyStateView` — illustrated empty states
- `SearchBar` — searchable with debounce
- `AccountCard` — account balance summary card

---

### Phase 5: UI — Screens

#### [NEW] Navigation (`ui/navigation/`)
- `AppNavHost.kt` — NavHost with all routes
- `BottomNavBar.kt` — 5-tab bottom bar (Home, Add FAB, Transactions, Budgets/Goals, Reports)
- `Screen.kt` — sealed class for all routes

#### [NEW] Home Screen (`ui/home/`)
- `HomeScreen.kt` — Dashboard layout per UI spec
- `HomeViewModel.kt` — aggregates balance, budget progress, recent transactions, quick-add data
- Current balance (large, bold, top)
- Period budget progress bar/ring
- Quick-add shortcut buttons (3-4 configurable)
- Mini spend chart (trend line or donut, tappable)
- Recent transactions (last 5-10)

#### [NEW] Add/Edit Transaction Screen (`ui/add/`)
- `AddTransactionScreen.kt` — Calculator-style numeric keypad dominant
- `AddTransactionViewModel.kt` — handles save/edit, bulk mode
- Category picker as horizontal scrollable icon chips
- Account & date in compact row
- Optional note field
- **Bill/receipt attachment** — tap to attach images from camera or gallery; multiple attachments per transaction; thumbnails shown inline; tap to view full-size; swipe to remove
- "Save" button always visible in lower half
- Bulk entry mode toggle
- Income/Expense/Transfer type selector

#### [NEW] Transactions Screen (`ui/transactions/`)
- `TransactionsScreen.kt` — full list with search, filter, sort
- `TransactionsViewModel.kt` — filtered queries, pagination
- `FilterSheet.kt` — bottom sheet with all filter options
- Saved filters quick access
- Swipe-to-delete with undo

#### [NEW] Budgets & Goals Screen (`ui/budgets/`)
- `BudgetsScreen.kt` — budget progress per category, overall budget
- `GoalsScreen.kt` — savings goals with progress
- `BudgetViewModel.kt`, `GoalViewModel.kt`
- Add/edit budget dialog
- Add/edit goal dialog
- Transfer-to-goal action

#### [NEW] Reports Screen (`ui/reports/`)
- `ReportsScreen.kt` — all 10 chart types from Section 10
- `ReportsViewModel.kt` — data aggregation for all charts
- Custom date range picker
- Charts:
  1. Category breakdown (pie/donut)
  2. Spending trend (line chart — daily/weekly/monthly toggle)
  3. Income vs expense (bar chart by month)
  4. Budget vs actual (progress bars)
  5. Account balance overview (summary cards)
  6. Top spending categories (ranked bar)
  7. Cash flow calendar/heatmap
  8. Net worth/savings trend (line chart)
  9. Lending/borrowing summary (list view)
  10. Year-in-review (annual summary stats)

#### [NEW] Settings Screen (`ui/settings/`)
- Theme toggle (light/dark/system)
- App lock (PIN / biometric)
- Currency settings
- Category management
- Account management
- Quick-add shortcut management
- Export/import
- About / version

#### [NEW] Additional Screens
- `ui/accounts/` — Account list, add/edit account
- `ui/categories/` — Category & subcategory management, drag-to-reorder
- `ui/lending/` — Lending/borrowing list, add/edit, settlement
- `ui/search/` — Advanced search with saved filters

---

### Phase 6: Background Services & Notifications

#### [NEW] Notification System (`notification/`)
- `NotificationHelper.kt` — channel creation, notification building
- `BudgetAlertWorker.kt` — WorkManager periodic check for budget thresholds (80%, 100%)
- `ReminderWorker.kt` — bill/credit card due date reminders
- `RecurringTransactionWorker.kt` — auto-generates recurring entries
- `UnusualSpendingWorker.kt` — flags transactions >2x trailing 3-month category average
- `WeeklySummaryWorker.kt` — weekly/monthly recap notification

#### [NEW] Security (`security/`)
- `AppLockManager.kt` — PIN verification, biometric prompt
- `KeystoreHelper.kt` — encryption key generation & retrieval
- `LockScreen.kt` — PIN entry / biometric composable

#### [NEW] Widget (`widget/`)
- `ExpenseWidgetProvider.kt` — `AppWidgetProvider` implementation
- `widget_layout.xml` — widget layout (balance + today's spend)
- `ExpenseWidgetReceiver.kt` — broadcast receiver for updates

---

### Phase 7: Export/Import

#### [NEW] Export/Import (`data/export/`)
- `CsvExporter.kt` — transaction export to CSV for date range
- `PdfExporter.kt` — formatted PDF report generation
- `CsvImporter.kt` — CSV import with column mapping UI
- `FullBackupManager.kt` — serialize entire DB (JSON + SQLite file) **plus all attachment images** to a single `.expbak` zip file, restore on import

#### [NEW] Attachment Storage (`data/attachment/`)
- `AttachmentStorage.kt` — copies images to app-internal storage (`filesDir/attachments/`), generates thumbnails, handles cleanup on transaction delete

---

## Verification Plan

### Automated Tests
```bash
./gradlew assembleDebug           # Verify project compiles
./gradlew testDebugUnitTest       # Run unit tests
./gradlew connectedDebugAndroidTest  # Run instrumented tests (if emulator available)
```

### Build Verification
- Ensure `assembleDebug` succeeds with zero errors
- Verify all Room entity schemas generate correctly
- Verify Hilt dependency graph resolves

### Manual Verification
- Install on emulator/device
- Test full transaction flow: add expense → view in transactions → see in reports
- Test budget creation and alert threshold
- Test export CSV → import CSV roundtrip
- Test full backup → fresh install → restore
- Test dark/light theme switching
- Test app lock with PIN

---

## File Count Estimate

| Layer | Estimated Files |
|-------|----------------|
| Gradle/Config | ~5 |
| Data (Entities) | ~16 |
| Data (DAOs) | ~14 |
| Data (Repositories) | ~10 |
| Domain (Use Cases) | ~10 |
| UI (Theme/Components) | ~15 |
| UI (Screens + ViewModels) | ~30 |
| DI Modules | ~3 |
| Services/Workers | ~8 |
| Security | ~4 |
| Widget | ~3 |
| Export/Import | ~5 |
| Util | ~5 |
| **Total** | **~128 files** |

This is a substantial codebase. I will build it systematically phase by phase.
