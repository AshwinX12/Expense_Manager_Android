package com.expensemanager.app.ui.settings

import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.expensemanager.app.data.db.entity.ThemeMode
import com.expensemanager.app.data.db.entity.ThemeStyle
import com.expensemanager.app.data.repository.SettingsRepository
import com.expensemanager.app.data.repository.TransactionRepository
import com.expensemanager.app.ui.theme.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

private fun themeStyleLabel(style: ThemeStyle): String = when (style) {
    ThemeStyle.CLASSIC -> "Classic"
    ThemeStyle.WARM_PAPER -> "Warm Paper"
    ThemeStyle.EDITORIAL -> "Editorial"
    ThemeStyle.MEMPHIS -> "Memphis Geometric"
    ThemeStyle.TERMINAL -> "Terminal"
}

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    val themeMode = settingsRepository.getThemeModeFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ThemeMode.SYSTEM)

    val themeStyle = settingsRepository.getThemeStyleFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ThemeStyle.CLASSIC)

    val currencyCode = settingsRepository.getBaseCurrencyFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "INR")

    // Security state
    private val _appLockEnabled = MutableStateFlow(false)
    private val _biometricEnabled = MutableStateFlow(false)
    private val _pin = MutableStateFlow("")
    val appLockEnabled: StateFlow<Boolean> = _appLockEnabled
    val biometricEnabled: StateFlow<Boolean> = _biometricEnabled
    val currentPin: StateFlow<String> = _pin

    // Export state
    private val _exportMessage = MutableStateFlow<String?>(null)
    val exportMessage: StateFlow<String?> = _exportMessage

    val swipeNavigationEnabled = settingsRepository.isSwipeNavigationEnabledFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    init {
        viewModelScope.launch {
            _appLockEnabled.value = settingsRepository.isAppLockEnabled()
            _biometricEnabled.value = settingsRepository.isBiometricEnabled()
            _pin.value = settingsRepository.getPin() ?: ""
        }
    }

    fun setTheme(mode: ThemeMode) {
        viewModelScope.launch { settingsRepository.setThemeMode(mode) }
    }

    fun setThemeStyle(style: ThemeStyle) {
        viewModelScope.launch { settingsRepository.setThemeStyle(style) }
    }

    fun setCurrency(code: String) {
        viewModelScope.launch { settingsRepository.setBaseCurrency(code) }
    }

    fun setSwipeNavigationEnabled(enabled: Boolean) {
        viewModelScope.launch { settingsRepository.setSwipeNavigationEnabled(enabled) }
    }

    fun setAppLock(enabled: Boolean) {
        _appLockEnabled.value = enabled
        viewModelScope.launch { settingsRepository.setAppLockEnabled(enabled) }
    }

    fun setBiometric(enabled: Boolean) {
        _biometricEnabled.value = enabled
        viewModelScope.launch { settingsRepository.setBiometricEnabled(enabled) }
    }

    fun savePin(pin: String) {
        _pin.value = pin
        viewModelScope.launch { settingsRepository.setPin(pin) }
    }

    fun savePinAndEnableLock(pin: String) {
        _pin.value = pin
        _appLockEnabled.value = true
        viewModelScope.launch {
            // Save PIN first, then enable lock — order matters
            settingsRepository.setPin(pin)
            settingsRepository.setAppLockEnabled(true)
        }
    }

    fun clearAppLock() {
        _appLockEnabled.value = false
        _biometricEnabled.value = false
        _pin.value = ""
        viewModelScope.launch {
            settingsRepository.setAppLockEnabled(false)
            settingsRepository.setBiometricEnabled(false)
            settingsRepository.setPin("")
        }
    }

    private fun saveToDownloads(context: Context, filename: String, mimeType: String, writeContent: (java.io.OutputStream) -> Unit) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            val resolver = context.contentResolver
            val contentValues = android.content.ContentValues().apply {
                put(android.provider.MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(android.provider.MediaStore.MediaColumns.MIME_TYPE, mimeType)
                put(android.provider.MediaStore.MediaColumns.RELATIVE_PATH, android.os.Environment.DIRECTORY_DOWNLOADS)
            }
            val uri = resolver.insert(android.provider.MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
            if (uri != null) {
                resolver.openOutputStream(uri)?.use { output ->
                    writeContent(output)
                }
            } else {
                throw Exception("Failed to create MediaStore entry")
            }
        } else {
            val downloadsDir = android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_DOWNLOADS)
            if (!downloadsDir.exists()) downloadsDir.mkdirs()
            val file = File(downloadsDir, filename)
            file.outputStream().use { output ->
                writeContent(output)
            }
        }
    }

    fun exportCsv(context: Context) {
        viewModelScope.launch {
            try {
                val transactions = transactionRepository.getAll()
                val timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))
                val filename = "transactions_$timestamp.csv"
                
                saveToDownloads(context, filename, "text/csv") { outputStream ->
                    outputStream.bufferedWriter().use { writer ->
                        writer.write("Date,Type,Amount,Category,Account,Note,Merchant\n")
                        transactions.forEach { t ->
                            writer.write(
                                "${t.date},${t.type},${t.amount},${t.categoryId ?: ""},${t.accountId ?: ""},\"${t.note?.replace("\"", "\"\"") ?: ""}\",\"${t.merchantName?.replace("\"", "\"\"") ?: ""}\"\n"
                            )
                        }
                    }
                }
                
                _exportMessage.value = "Saved $filename to Downloads"
            } catch (e: Exception) {
                _exportMessage.value = "Export failed: ${e.message}"
            }
        }
    }

    fun exportPdf(context: Context) {
        viewModelScope.launch {
            try {
                val transactions = transactionRepository.getAll()
                val pdfDocument = android.graphics.pdf.PdfDocument()
                val pageInfo = android.graphics.pdf.PdfDocument.PageInfo.Builder(595, 842, 1).create() // A4
                var page = pdfDocument.startPage(pageInfo)
                var canvas = page.canvas
                val paint = android.graphics.Paint().apply {
                    textSize = 12f
                    color = android.graphics.Color.BLACK
                }
                
                var yPosition = 50f
                canvas.drawText("Expense Manager - Transaction Report", 50f, yPosition, paint.apply { textSize = 18f; isFakeBoldText = true })
                yPosition += 40f
                paint.textSize = 12f
                paint.isFakeBoldText = false
                
                transactions.forEach { t ->
                    if (yPosition > 800f) {
                        pdfDocument.finishPage(page)
                        page = pdfDocument.startPage(pageInfo)
                        canvas = page.canvas
                        yPosition = 50f
                    }
                    val text = "${t.date} | ${t.type.name} | ${com.expensemanager.app.util.AppCurrency.symbol}${t.amount} | ${t.note ?: ""}"
                    canvas.drawText(text, 50f, yPosition, paint)
                    yPosition += 20f
                }
                pdfDocument.finishPage(page)
                
                val timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))
                val filename = "transactions_$timestamp.pdf"
                
                saveToDownloads(context, filename, "application/pdf") { outputStream ->
                    pdfDocument.writeTo(outputStream)
                }
                pdfDocument.close()
                
                _exportMessage.value = "Saved $filename to Downloads"
            } catch (e: Exception) {
                _exportMessage.value = "PDF Export failed: ${e.message}"
            }
        }
    }

    fun exportDatabase(context: Context) {
        viewModelScope.launch {
            try {
                val dbFile = context.getDatabasePath("expense_manager.db")
                if (!dbFile.exists()) {
                    _exportMessage.value = "Database file not found."
                    return@launch
                }
                
                val timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))
                val filename = "expense_manager_backup_$timestamp.db"
                
                saveToDownloads(context, filename, "application/octet-stream") { outputStream ->
                    dbFile.inputStream().use { input ->
                        input.copyTo(outputStream)
                    }
                }
                
                _exportMessage.value = "Backup saved to Downloads: $filename"
            } catch (e: Exception) {
                _exportMessage.value = "Database export failed: ${e.message}"
            }
        }
    }

    fun importDatabase(context: Context, uri: android.net.Uri) {
        viewModelScope.launch {
            try {
                val dbFile = context.getDatabasePath("expense_manager.db")
                
                // Write to a temporary file to validate
                val tempFile = File(context.cacheDir, "temp_restore.db")
                context.contentResolver.openInputStream(uri)?.use { input ->
                    tempFile.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
                
                // Validate standard SQLite database
                var isValidDb = false
                try {
                    val db = android.database.sqlite.SQLiteDatabase.openDatabase(
                        tempFile.absolutePath,
                        null,
                        android.database.sqlite.SQLiteDatabase.OPEN_READONLY
                    )
                    db.rawQuery("SELECT COUNT(*) FROM sqlite_schema", null).use { cursor ->
                        cursor.moveToFirst()
                    }
                    db.close()
                    isValidDb = true
                } catch (e: Exception) {
                    android.util.Log.e("DB_RESTORE", "Validation failed", e)
                    // Ignore, isValidDb remains false
                }
                
                if (!isValidDb) {
                    _exportMessage.value = "Invalid backup file. Please select a valid database backup."
                    tempFile.delete()
                    return@launch
                }
                
                // Replace the actual database
                tempFile.copyTo(dbFile, overwrite = true)
                tempFile.delete()
                
                // Delete journal/wal files to prevent corruption
                context.getDatabasePath("expense_manager.db-wal").delete()
                context.getDatabasePath("expense_manager.db-shm").delete()

                _exportMessage.value = "Database restored. Please restart the app."
            } catch (e: Exception) {
                _exportMessage.value = "Restore failed: ${e.message}"
            }
        }
    }

    fun importCsv(context: Context, uri: android.net.Uri) {
        viewModelScope.launch {
            try {
                var count = 0
                context.contentResolver.openInputStream(uri)?.bufferedReader()?.useLines { lines ->
                    val iterator = lines.iterator()
                    if (iterator.hasNext()) {
                        iterator.next() // Skip header
                    }
                    while (iterator.hasNext()) {
                        val line = iterator.next()
                        if (line.isNotBlank()) {
                            // Date,Type,Amount,Category,Account,Note,Merchant
                            // Simple split by comma. Note: Doesn't handle commas inside quotes perfectly, but sufficient for basic import.
                            val parts = line.split(",")
                            if (parts.size >= 3) {
                                val dateStr = parts[0].trim()
                                val typeStr = parts[1].trim()
                                val amountStr = parts[2].trim()
                                
                                val amount = amountStr.toBigDecimalOrNull() ?: continue
                                val type = try { com.expensemanager.app.data.db.entity.TransactionType.valueOf(typeStr) } catch (e: Exception) { com.expensemanager.app.data.db.entity.TransactionType.EXPENSE }
                                val date = try { java.time.LocalDate.parse(dateStr) } catch (e: Exception) { java.time.LocalDate.now() }
                                
                                val tx = com.expensemanager.app.data.db.entity.TransactionEntity(
                                    amount = amount,
                                    type = type,
                                    date = date
                                )
                                transactionRepository.insert(tx)
                                count++
                            }
                        }
                    }
                }
                _exportMessage.value = "Imported $count transactions"
            } catch (e: Exception) {
                _exportMessage.value = "CSV Import failed: ${e.message}"
            }
        }
    }

    fun clearExportMessage() {
        _exportMessage.value = null
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    onNavigateToAccounts: () -> Unit,
    onNavigateToCategories: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()
    val themeStyle by viewModel.themeStyle.collectAsStateWithLifecycle()
    val currencyCode by viewModel.currencyCode.collectAsStateWithLifecycle()
    val swipeNavigationEnabled by viewModel.swipeNavigationEnabled.collectAsStateWithLifecycle()
    val appLockEnabled by viewModel.appLockEnabled.collectAsStateWithLifecycle()
    val biometricEnabled by viewModel.biometricEnabled.collectAsStateWithLifecycle()
    val currentPin by viewModel.currentPin.collectAsStateWithLifecycle()
    val exportMessage by viewModel.exportMessage.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var showSecurityDialog by remember { mutableStateOf(false) }
    var showComingSoonDialog by remember { mutableStateOf<String?>(null) }

    val snackbarHostState = remember { SnackbarHostState() }
    
    val dbPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { viewModel.importDatabase(context, it) }
    }

    val csvPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { viewModel.importCsv(context, it) }
    }

    LaunchedEffect(exportMessage) {
        exportMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearExportMessage()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", fontWeight = FontWeight.SemiBold) },
                windowInsets = WindowInsets(0.dp),
                colors = com.expensemanager.app.ui.components.flatTopAppBarColors(),
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(vertical = Dimens.SpacingSm)
        ) {
            // Appearance
            item { SettingsSectionHeader("Appearance") }
            item {
                var expanded by remember { mutableStateOf(false) }
                SettingsItem(
                    icon = Icons.Default.DarkMode,
                    title = "Theme",
                    trailingContent = {
                        Box {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = themeMode.name.lowercase().replaceFirstChar { it.uppercase() },
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(Icons.Default.ArrowDropDown, contentDescription = "Select Theme", modifier = Modifier.size(20.dp))
                            }
                            DropdownMenu(
                                expanded = expanded, 
                                onDismissRequest = { expanded = false },
                            ) {
                                ThemeMode.entries.forEach { mode ->
                                    DropdownMenuItem(
                                        text = { Text(mode.name.lowercase().replaceFirstChar { it.uppercase() }) },
                                        onClick = { viewModel.setTheme(mode); expanded = false },
                                        leadingIcon = {
                                            if (themeMode == mode) Icon(Icons.Default.Check, null, tint = MaterialTheme.colorScheme.primary)
                                        }
                                    )
                                }
                            }
                        }
                    },
                    onClick = { expanded = true }
                )
            }
            item {
                var expanded by remember { mutableStateOf(false) }
                SettingsItem(
                    icon = Icons.Default.Palette,
                    title = "Theme Style",
                    trailingContent = {
                        Box {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = themeStyleLabel(themeStyle),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(Icons.Default.ArrowDropDown, contentDescription = "Select Theme Style", modifier = Modifier.size(20.dp))
                            }
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                            ) {
                                ThemeStyle.entries.forEach { style ->
                                    DropdownMenuItem(
                                        text = { Text(themeStyleLabel(style)) },
                                        onClick = { viewModel.setThemeStyle(style); expanded = false },
                                        leadingIcon = {
                                            if (themeStyle == style) Icon(Icons.Default.Check, null, tint = MaterialTheme.colorScheme.primary)
                                        }
                                    )
                                }
                            }
                        }
                    },
                    onClick = { expanded = true }
                )
            }
            item {
                var expanded by remember { mutableStateOf(false) }
                val selected = com.expensemanager.app.util.SUPPORTED_CURRENCIES
                    .firstOrNull { it.code == currencyCode }
                    ?: com.expensemanager.app.util.SUPPORTED_CURRENCIES.first()
                SettingsItem(
                    icon = Icons.Default.Payments,
                    title = "Currency",
                    trailingContent = {
                        Box {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "${selected.symbol}  ${selected.code}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(Icons.Default.ArrowDropDown, contentDescription = "Select Currency", modifier = Modifier.size(20.dp))
                            }
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                            ) {
                                com.expensemanager.app.util.SUPPORTED_CURRENCIES.forEach { option ->
                                    DropdownMenuItem(
                                        text = { Text("${option.symbol}  ${option.code} · ${option.label}") },
                                        onClick = { viewModel.setCurrency(option.code); expanded = false },
                                        leadingIcon = {
                                            if (currencyCode == option.code) {
                                                Icon(Icons.Default.Check, null, tint = MaterialTheme.colorScheme.primary)
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    },
                    onClick = { expanded = true }
                )
            }
            item {
                SettingsSwitchItem(
                    icon = Icons.Default.SwipeRightAlt,
                    title = "Swipe Navigation",
                    subtitle = if (swipeNavigationEnabled) "Enabled" else "Disabled",
                    checked = swipeNavigationEnabled,
                    onCheckedChange = { viewModel.setSwipeNavigationEnabled(it) }
                )
            }

            // Data management
            item { SettingsSectionHeader("Data Management") }
            item { SettingsItem(Icons.Default.AccountBalance, "Manage Accounts", onClick = onNavigateToAccounts) }
            item { SettingsItem(Icons.Default.Category, "Manage Categories", onClick = onNavigateToCategories) }

            // Export/Import
            item { SettingsSectionHeader("Backup & Export") }
            item {
                SettingsItem(
                    Icons.Default.FileDownload, "Export CSV",
                    subtitle = "Share transactions as CSV file",
                    onClick = { viewModel.exportCsv(context) }
                )
            }
            item {
                SettingsItem(Icons.Default.PictureAsPdf, "Export PDF",
                    subtitle = "Generate PDF report",
                    onClick = { viewModel.exportPdf(context) }
                )
            }
            item {
                SettingsItem(Icons.Default.FileUpload, "Import CSV",
                    subtitle = "Import transactions from CSV",
                    onClick = { csvPickerLauncher.launch("*/*") }
                )
            }
            item {
                SettingsItem(Icons.Default.Backup, "Full Backup",
                    subtitle = "Export encrypted backup for device migration",
                    onClick = { viewModel.exportDatabase(context) }
                )
            }
            item {
                SettingsItem(Icons.Default.Restore, "Restore Backup",
                    subtitle = "Import from backup file",
                    onClick = { dbPickerLauncher.launch("*/*") }
                )
            }

            // Security
            item { SettingsSectionHeader("Security") }
            item {
                SettingsItem(
                    Icons.Default.Lock, "App Lock",
                    subtitle = if (appLockEnabled) "PIN & biometric enabled" else "Tap to configure",
                    onClick = { showSecurityDialog = true }
                )
            }

            // About
            item { SettingsSectionHeader("About") }
            item { SettingsItem(Icons.Default.Info, "Version", subtitle = "1.0.0") }
        }
    }

    // ── Security dialog ────────────────────────────────────────────────────────
    if (showSecurityDialog) {
        AppLockDialog(
            appLockEnabled = appLockEnabled,
            biometricEnabled = biometricEnabled,
            currentPin = currentPin,
            onSavePinAndEnable = { viewModel.savePinAndEnableLock(it) },
            onClearAppLock = { viewModel.clearAppLock() },
            onSetBiometric = { viewModel.setBiometric(it) },
            onSavePin = { viewModel.savePin(it) },
            onDismiss = { showSecurityDialog = false }
        )
    }

    // ── Coming soon dialog ─────────────────────────────────────────────────────
    showComingSoonDialog?.let { feature ->
        AlertDialog(
            onDismissRequest = { showComingSoonDialog = null },
            icon = { Icon(Icons.Default.BuildCircle, null) },
            title = { Text(feature) },
            text = { Text("$feature is coming in a future update. Your data is always safe.") },
            confirmButton = {
                Button(onClick = { showComingSoonDialog = null }) { Text("Got it") }
            }
        )
    }
}

// ── App Lock Dialog ────────────────────────────────────────────────────────────

@Composable
private fun AppLockDialog(
    appLockEnabled: Boolean,
    biometricEnabled: Boolean,
    currentPin: String,
    onSavePinAndEnable: (String) -> Unit,
    onClearAppLock: () -> Unit,
    onSetBiometric: (Boolean) -> Unit,
    onSavePin: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var newPin by remember { mutableStateOf("") }
    var confirmPin by remember { mutableStateOf("") }
    var pinError by remember { mutableStateOf<String?>(null) }
    // Track whether the user wants to enable but hasn't set a PIN yet
    var pendingEnable by remember { mutableStateOf(false) }
    // Show PIN/biometric section when already enabled OR pending enable
    val showSecuritySection = appLockEnabled || pendingEnable

    AlertDialog(
        onDismissRequest = onDismiss,
        icon = { Icon(Icons.Default.Lock, null) },
        title = { Text("App Lock", fontWeight = FontWeight.SemiBold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(Dimens.SpacingMd)) {
                // App lock toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Enable App Lock", style = MaterialTheme.typography.bodyMedium)
                        Text("Require PIN on open", style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Switch(
                        checked = appLockEnabled || pendingEnable,
                        onCheckedChange = { enabled ->
                            if (enabled) {
                                // Don't persist yet — wait for PIN to be set
                                pendingEnable = true
                            } else {
                                // Disable immediately and clear everything
                                pendingEnable = false
                                onClearAppLock()
                            }
                        }
                    )
                }

                if (showSecuritySection) {
                    HorizontalDivider()

                    if (!appLockEnabled) {
                        // Pending enable — user must set a PIN first
                        Text(
                            "Set a 4-digit PIN to enable app lock",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    // PIN setup fields
                    OutlinedTextField(
                        value = newPin,
                        onValueChange = { if (it.length <= 4 && it.all { c -> c.isDigit() }) newPin = it },
                        label = { Text(if (appLockEnabled && currentPin.isNotEmpty()) "New PIN" else "PIN") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = confirmPin,
                        onValueChange = { if (it.length <= 4 && it.all { c -> c.isDigit() }) confirmPin = it },
                        label = { Text("Confirm PIN") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        isError = pinError != null,
                        supportingText = pinError?.let { { Text(it, color = MaterialTheme.colorScheme.error) } }
                    )
                    if (newPin.length == 4) {
                        Button(
                            onClick = {
                                if (newPin == confirmPin) {
                                    if (!appLockEnabled) {
                                        // First time: save PIN and enable lock atomically
                                        onSavePinAndEnable(newPin)
                                    } else {
                                        // Already enabled: just update the PIN
                                        onSavePin(newPin)
                                    }
                                    pendingEnable = false
                                    newPin = ""
                                    confirmPin = ""
                                    pinError = null
                                } else {
                                    pinError = "PINs don't match"
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = newPin.length == 4 && confirmPin.length == 4
                        ) {
                            Text(if (appLockEnabled) "Save PIN" else "Set PIN & Enable Lock")
                        }
                    }

                    // Biometric toggle — only show when app lock is actually enabled
                    if (appLockEnabled) {
                        HorizontalDivider()

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("Biometric Unlock", style = MaterialTheme.typography.bodyMedium)
                                Text("Fingerprint / Face ID", style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Switch(checked = biometricEnabled, onCheckedChange = onSetBiometric)
                        }

                        if (currentPin.isNotEmpty()) {
                            HorizontalDivider()
                            Text(
                                "Change PIN",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                // If user dismisses while pending, cancel the pending enable
                if (pendingEnable && !appLockEnabled) {
                    pendingEnable = false
                }
                onDismiss()
            }) { Text("Done") }
        }
    )
}

// ── Shared components ──────────────────────────────────────────────────────────

@Composable
private fun SettingsSectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(horizontal = Dimens.ScreenPadding, vertical = Dimens.SpacingSm)
    )
}

@Composable
private fun SettingsItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    onClick: () -> Unit = {}
) {
    ListItem(
        headlineContent = { Text(title) },
        supportingContent = subtitle?.let { { Text(it, style = MaterialTheme.typography.bodySmall) } },
        leadingContent = { Icon(icon, null, tint = MaterialTheme.colorScheme.onSurfaceVariant) },
        trailingContent = trailingContent ?: { Icon(Icons.Default.ChevronRight, null, Modifier.size(18.dp)) },
        modifier = Modifier.clickable(onClick = onClick)
    )
}

@Composable
private fun SettingsSwitchItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String? = null,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    ListItem(
        headlineContent = { Text(title) },
        supportingContent = subtitle?.let { { Text(it, style = MaterialTheme.typography.bodySmall) } },
        leadingContent = { Icon(icon, null, tint = MaterialTheme.colorScheme.onSurfaceVariant) },
        trailingContent = { Switch(checked = checked, onCheckedChange = null) },
        modifier = Modifier.clickable { onCheckedChange(!checked) }
    )
}
