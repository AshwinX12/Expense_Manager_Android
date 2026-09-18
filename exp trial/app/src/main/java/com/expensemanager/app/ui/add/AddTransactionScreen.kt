package com.expensemanager.app.ui.add

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.expensemanager.app.data.db.entity.TransactionType
import com.expensemanager.app.ui.components.*
import com.expensemanager.app.ui.theme.*
import java.io.File
import java.time.format.DateTimeFormatter

private val categoryColorPalette = listOf(
    "#FF6B6B", "#4ECDC4", "#45B7D1", "#96CEB4",
    "#FFEAA7", "#DDA0DD", "#98D8C8", "#F7DC6F"
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AddTransactionScreen(
    onNavigateBack: () -> Unit,
    transactionId: Long?,
    viewModel: AddTransactionViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val keyboardController = androidx.compose.ui.platform.LocalSoftwareKeyboardController.current

    // Bottom sheet states
    var showCreateCategorySheet by remember { mutableStateOf(false) }

    LaunchedEffect(transactionId) {
        transactionId?.let { viewModel.loadTransaction(it) }
    }

    LaunchedEffect(state.savedSuccessfully) {
        if (state.savedSuccessfully && !state.bulkMode) {
            onNavigateBack()
        }
        if (state.savedSuccessfully) {
            viewModel.clearSavedFlag()
        }
    }

    // Auto-hide numpad when the system keyboard (IME) appears — e.g. when a text field is focused
    val imeVisible = WindowInsets.isImeVisible
    LaunchedEffect(imeVisible) {
        if (imeVisible && state.numpadVisible) {
            viewModel.toggleNumpad()
        }
    }

    // Handle back button for numpad
    BackHandler(enabled = state.numpadVisible) {
        viewModel.toggleNumpad()
    }

    var tempPhotoUri by remember { mutableStateOf<Uri?>(null) }
    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && tempPhotoUri != null) viewModel.addAttachmentUri(tempPhotoUri!!)
    }

    val launchCamera = {
        try {
            val dir = File(context.filesDir, "attachments")
            dir.mkdirs()
            val photoFile = File(dir, "temp_photo_${System.currentTimeMillis()}.jpg")
            tempPhotoUri = FileProvider.getUriForFile(
                context,
                "${com.expensemanager.app.BuildConfig.APPLICATION_ID}.fileprovider",
                photoFile
            )
            cameraLauncher.launch(tempPhotoUri!!)
        } catch (e: android.content.ActivityNotFoundException) {
            android.widget.Toast.makeText(context, "No camera app found", android.widget.Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            android.widget.Toast.makeText(context, "Error: ${e.message}", android.widget.Toast.LENGTH_LONG).show()
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            launchCamera()
        } else {
            android.widget.Toast.makeText(context, "Camera permission denied", android.widget.Toast.LENGTH_SHORT).show()
        }
    }
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri -> 
        uri?.let { 
            try {
                context.contentResolver.takePersistableUriPermission(it, android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION)
            } catch (e: Exception) {
                // Ignore if permission can't be persisted
            }
            viewModel.addAttachmentUri(it) 
        } 
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (state.isEditing) "Edit Transaction" else "Add Transaction",
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (state.numpadVisible) {
                            viewModel.toggleNumpad()
                        } else if (imeVisible) {
                            keyboardController?.hide()
                        } else {
                            onNavigateBack()
                        }
                    }) {
                        Icon(Icons.Default.Close, "Close")
                    }
                },
                actions = {
                    if (!state.isEditing) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Bulk", style = MaterialTheme.typography.labelSmall)
                            Spacer(modifier = Modifier.width(8.dp))
                            Switch(
                                checked = state.bulkMode,
                                onCheckedChange = { viewModel.setBulkMode(it) },
                                modifier = Modifier.padding(end = 8.dp)
                            )
                        }
                    }
                },
                windowInsets = WindowInsets(0.dp),
                colors = flatTopAppBarColors()
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // ── Scrollable form area ──────────────────────────────────────────
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                // Type selector
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.ScreenPadding),
                    horizontalArrangement = Arrangement.spacedBy(Dimens.SpacingSm)
                ) {
                    TransactionType.entries.filter { 
                        it == TransactionType.EXPENSE || it == TransactionType.INCOME 
                    }.forEach { type ->
                        FilterChip(
                            selected = state.type == type,
                            onClick = { viewModel.setType(type) },
                            label = {
                                Text(
                                    when (type) {
                                        TransactionType.EXPENSE -> "Expense"
                                        TransactionType.INCOME -> "Income"
                                        else -> ""
                                    }
                                )
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(Dimens.SpacingLg))

                // ── Tappable amount display (toggles numpad) ──────────────────
                val amountColor by animateColorAsState(
                    targetValue = when (state.type) {
                        TransactionType.EXPENSE -> ExpenseRed
                        TransactionType.INCOME -> IncomeGreen
                        else -> MaterialTheme.colorScheme.onSurface
                    },
                    animationSpec = tween(300),
                    label = "amountColor"
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { 
                            if (!state.numpadVisible) {
                                keyboardController?.hide()
                            }
                            viewModel.toggleNumpad() 
                        }
                        .padding(vertical = Dimens.SpacingMd),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "${com.expensemanager.app.util.AppCurrency.symbol}${state.amountText}",
                            style = MaterialTheme.typography.displayLarge,
                            fontWeight = FontWeight.Bold,
                            color = amountColor,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = if (state.numpadVisible)
                                Icons.Default.KeyboardArrowDown
                            else
                                Icons.Default.Edit,
                            contentDescription = if (state.numpadVisible) "Hide keypad" else "Edit amount",
                            tint = amountColor.copy(alpha = 0.6f),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(Dimens.SpacingSm))

                // ── Category chips with "+ New" ───────────────────────────────
                Text(
                    text = "Category",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(horizontal = Dimens.ScreenPadding),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(Dimens.SpacingXs))
                CategoryChipRow(
                    categories = state.categories,
                    selectedId = state.categoryId,
                    onSelect = { viewModel.setCategory(it.id) },
                    onCreateNew = { showCreateCategorySheet = true }
                )

                Spacer(modifier = Modifier.height(Dimens.SpacingLg))

                // Account and date row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.ScreenPadding),
                    horizontalArrangement = Arrangement.spacedBy(Dimens.SpacingSm)
                ) {
                    var accountExpanded by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox(
                        expanded = accountExpanded,
                        onExpandedChange = { accountExpanded = it },
                        modifier = Modifier.weight(1f)
                    ) {
                        OutlinedTextField(
                            value = state.accounts.find { it.id == state.accountId }?.name ?: "Account",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Account") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(accountExpanded) },
                            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable),
                            singleLine = true
                        )
                        ExposedDropdownMenu(
                            expanded = accountExpanded,
                            onDismissRequest = { accountExpanded = false }
                        ) {
                            state.accounts.forEach { account ->
                                DropdownMenuItem(
                                    text = { Text(account.name) },
                                    onClick = {
                                        viewModel.setAccount(account.id)
                                        accountExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    var showDatePicker by remember { mutableStateOf(false) }
                    OutlinedTextField(
                        value = state.date.format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Date") },
                        modifier = Modifier
                            .weight(1f)
                            .clickable { showDatePicker = true },
                        singleLine = true,
                        trailingIcon = {
                            IconButton(onClick = { showDatePicker = true }) {
                                Icon(Icons.Default.CalendarToday, "Pick date")
                            }
                        }
                    )

                    var showTimePicker by remember { mutableStateOf(false) }
                    OutlinedTextField(
                        value = state.time?.format(DateTimeFormatter.ofPattern("HH:mm")) ?: "Time",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Time") },
                        modifier = Modifier
                            .weight(1f)
                            .clickable { showTimePicker = true },
                        singleLine = true,
                        trailingIcon = {
                            IconButton(onClick = { showTimePicker = true }) {
                                Icon(Icons.Default.AccessTime, "Pick time")
                            }
                        }
                    )

                    if (showDatePicker) {
                        val datePickerState = rememberDatePickerState(
                            initialSelectedDateMillis = state.date.toEpochDay() * 86400000
                        )
                        DatePickerDialog(
                            onDismissRequest = { showDatePicker = false },
                            confirmButton = {
                                TextButton(onClick = {
                                    datePickerState.selectedDateMillis?.let { millis ->
                                        viewModel.setDate(
                                            java.time.Instant.ofEpochMilli(millis)
                                                .atZone(java.time.ZoneId.systemDefault())
                                                .toLocalDate()
                                        )
                                    }
                                    showDatePicker = false
                                }) { Text("OK") }
                            },
                            dismissButton = {
                                TextButton(onClick = { showDatePicker = false }) { Text("Cancel") }
                            }
                        ) {
                            DatePicker(state = datePickerState)
                        }
                    }

                    if (showTimePicker) {
                        val timePickerState = rememberTimePickerState(
                            initialHour = state.time?.hour ?: java.time.LocalTime.now().hour,
                            initialMinute = state.time?.minute ?: java.time.LocalTime.now().minute
                        )
                        AlertDialog(
                            onDismissRequest = { showTimePicker = false },
                            confirmButton = {
                                TextButton(onClick = {
                                    viewModel.setTime(java.time.LocalTime.of(timePickerState.hour, timePickerState.minute))
                                    showTimePicker = false
                                }) { Text("OK") }
                            },
                            dismissButton = {
                                TextButton(onClick = { showTimePicker = false }) { Text("Cancel") }
                            },
                            text = {
                                TimePicker(state = timePickerState)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(Dimens.SpacingMd))

                // Note field
                OutlinedTextField(
                    value = state.note,
                    onValueChange = { viewModel.setNote(it) },
                    label = { Text("Note (optional)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.ScreenPadding),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(Dimens.SpacingSm))

                // Merchant field
                OutlinedTextField(
                    value = state.merchantName,
                    onValueChange = { viewModel.setMerchant(it) },
                    label = { Text("Merchant / Payee (optional)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.ScreenPadding),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(Dimens.SpacingMd))

                // ── Split Bill toggle ─────────────────────────────────────────
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.ScreenPadding)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { viewModel.toggleSplit() }
                        .background(
                            if (state.splitEnabled)
                                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                            else
                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                        )
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Group,
                            contentDescription = null,
                            tint = if (state.splitEnabled)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                        Column {
                            Text(
                                "Split this expense",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                color = if (state.splitEnabled)
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.onSurface
                            )
                            if (state.splitEnabled) {
                                Text(
                                    "${state.splitParticipants.size} people",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                    Switch(
                        checked = state.splitEnabled,
                        onCheckedChange = { viewModel.toggleSplit() }
                    )
                }

                // ── Split panel ───────────────────────────────────────────────
                AnimatedVisibility(
                    visible = state.splitEnabled,
                    enter = expandVertically(spring(stiffness = Spring.StiffnessMedium)) + fadeIn(),
                    exit = shrinkVertically(spring(stiffness = Spring.StiffnessMedium)) + fadeOut()
                ) {
                    SplitPanel(
                        state = state,
                        onAddPerson = { viewModel.addSplitParticipant() },
                        onRemovePerson = { viewModel.removeSplitParticipant(it) },
                        onUpdateName = { id, name -> viewModel.updateSplitName(id, name) },
                        onUpdateShare = { id, amount -> viewModel.updateSplitShare(id, amount) },
                        onToggleSettled = { viewModel.toggleSplitSettled(it) },
                        onSplitEvenly = { viewModel.splitEvenly() }
                    )
                }

                Spacer(modifier = Modifier.height(Dimens.SpacingMd))

                // Attachments
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.ScreenPadding),
                    horizontalArrangement = Arrangement.spacedBy(Dimens.SpacingSm),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Attach Bill",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.weight(1f)
                    )
                    FilledTonalIconButton(
                        onClick = {
                            if (androidx.core.content.ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                                launchCamera()
                            } else {
                                permissionLauncher.launch(android.Manifest.permission.CAMERA)
                            }
                        }
                    ) { Icon(Icons.Default.CameraAlt, "Take photo") }
                    FilledTonalIconButton(
                        onClick = { galleryLauncher.launch(arrayOf("image/*", "application/pdf")) }
                    ) { Icon(Icons.Default.Folder, "Choose from files") }
                }

                if (state.pendingAttachmentUris.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(Dimens.SpacingSm))
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = Dimens.ScreenPadding),
                        horizontalArrangement = Arrangement.spacedBy(Dimens.SpacingSm)
                    ) {
                        items(state.pendingAttachmentUris, key = { it.toString() }) { uri ->
                            Box(modifier = Modifier
                                .size(72.dp)
                                .clickable {
                                    try {
                                        val viewUri = if (uri.scheme == "file") {
                                            FileProvider.getUriForFile(
                                                context,
                                                "${com.expensemanager.app.BuildConfig.APPLICATION_ID}.fileprovider",
                                                java.io.File(uri.path!!)
                                            )
                                        } else uri
                                        
                                        val intent = android.content.Intent(android.content.Intent.ACTION_VIEW).apply {
                                            val mimeType = context.contentResolver.getType(viewUri) ?: "*/*"
                                            setDataAndType(viewUri, mimeType)
                                            addFlags(android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                        }
                                        context.startActivity(intent)
                                    } catch (e: Exception) {
                                        android.widget.Toast.makeText(context, "Could not open file: ${e.message}", android.widget.Toast.LENGTH_LONG).show()
                                    }
                                }
                            ) {
                                val isPdf = remember(uri) { 
                                    context.contentResolver.getType(uri)?.contains("pdf") == true || uri.toString().lowercase().endsWith(".pdf")
                                }
                                if (isPdf) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(MaterialTheme.shapes.small)
                                            .background(MaterialTheme.colorScheme.surfaceVariant),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            Icons.Default.PictureAsPdf,
                                            contentDescription = "PDF Document",
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(32.dp)
                                        )
                                    }
                                } else {
                                    AsyncImage(
                                        model = uri,
                                        contentDescription = "Bill attachment",
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(MaterialTheme.shapes.small),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                                IconButton(
                                    onClick = { viewModel.removeAttachmentUri(uri) },
                                    modifier = Modifier
                                        .size(16.dp)
                                        .align(Alignment.TopEnd)
                                        .background(MaterialTheme.colorScheme.error, CircleShape)
                                ) {
                                    Icon(
                                        Icons.Default.Close,
                                        contentDescription = "Remove",
                                        tint = MaterialTheme.colorScheme.onError,
                                        modifier = Modifier.size(10.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                state.error?.let { error ->
                    Spacer(modifier = Modifier.height(Dimens.SpacingSm))
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(horizontal = Dimens.ScreenPadding)
                    )
                }

                Spacer(modifier = Modifier.height(Dimens.SpacingLg))
            }

            // ── Bottom: Keypad (collapsible) + Save button ────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(Dimens.ScreenPadding)
            ) {
                AnimatedVisibility(
                    visible = state.numpadVisible,
                    enter = expandVertically(
                        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
                        expandFrom = Alignment.Bottom
                    ) + fadeIn(),
                    exit = shrinkVertically(
                        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
                        shrinkTowards = Alignment.Bottom
                    ) + fadeOut()
                ) {
                    Column {
                        NumericKeypad(
                            onDigit = { viewModel.onDigit(it) },
                            onDecimal = { viewModel.onDecimal() },
                            onBackspace = { viewModel.onBackspace() },
                            onClear = { viewModel.onClear() }
                        )
                        Spacer(modifier = Modifier.height(Dimens.SpacingMd))
                    }
                }

                Button(
                    onClick = { viewModel.save(transactionId) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    enabled = !state.isSaving,
                    shape = ButtonShape
                ) {
                    if (state.isSaving) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text(
                            text = if (state.bulkMode) "Save & Add Another" else "Save",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }

    // ── Create Category Bottom Sheet ──────────────────────────────────────────
    if (showCreateCategorySheet) {
        val themeStyle = LocalThemeStyle.current
        CreateCategorySheet(
            onDismiss = { showCreateCategorySheet = false },
            onSave = { name, colorHex ->
                viewModel.createCategory(name, colorHex)
                showCreateCategorySheet = false
            },
            palette = categoryPaletteFor(themeStyle),
            usedColors = state.categories.map { it.colorHex }
        )
    }
}

// ── Split Panel ───────────────────────────────────────────────────────────────

@Composable
private fun SplitPanel(
    state: AddTransactionUiState,
    onAddPerson: () -> Unit,
    onRemovePerson: (Int) -> Unit,
    onUpdateName: (Int, String) -> Unit,
    onUpdateShare: (Int, String) -> Unit,
    onToggleSettled: (Int) -> Unit,
    onSplitEvenly: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.ScreenPadding)
            .padding(top = Dimens.SpacingMd)
    ) {
        // Header row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Split between",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            TextButton(onClick = onSplitEvenly) {
                Icon(Icons.Default.Balance, contentDescription = null, modifier = Modifier.size(14.dp))
                Spacer(Modifier.width(4.dp))
                Text("Split evenly", style = MaterialTheme.typography.labelSmall)
            }
        }

        Spacer(modifier = Modifier.height(Dimens.SpacingXs))

        // Participant rows
        state.splitParticipants.forEachIndexed { index, participant ->
            SplitParticipantRow(
                participant = participant,
                canRemove = state.splitParticipants.size > 2,
                onRemove = { onRemovePerson(participant.id) },
                onUpdateName = { onUpdateName(participant.id, it) },
                onUpdateShare = { onUpdateShare(participant.id, it) },
                onToggleSettled = { onToggleSettled(participant.id) }
            )
            if (index < state.splitParticipants.lastIndex) {
                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), thickness = 0.5.dp)
            }
        }

        Spacer(modifier = Modifier.height(Dimens.SpacingSm))

        // Unallocated remainder
        val unallocated = state.splitUnallocated
        val unallocatedColor = when {
            unallocated > java.math.BigDecimal.ZERO -> MaterialTheme.colorScheme.onSurfaceVariant
            unallocated < java.math.BigDecimal.ZERO -> MaterialTheme.colorScheme.error
            else -> BudgetGood
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(unallocatedColor.copy(alpha = 0.08f))
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = when {
                    unallocated > java.math.BigDecimal.ZERO -> "Remaining to allocate"
                    unallocated < java.math.BigDecimal.ZERO -> "Over-allocated"
                    else -> "✓ Fully split"
                },
                style = MaterialTheme.typography.labelSmall,
                color = unallocatedColor
            )
            Text(
                text = "${com.expensemanager.app.util.AppCurrency.symbol}${unallocated.abs().toPlainString()}",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = unallocatedColor
            )
        }

        Spacer(modifier = Modifier.height(Dimens.SpacingSm))

        // Add person button
        OutlinedButton(
            onClick = onAddPerson,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Default.PersonAdd, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(Modifier.width(6.dp))
            Text("Add person")
        }

        Spacer(modifier = Modifier.height(Dimens.SpacingMd))
    }
}

@Composable
private fun SplitParticipantRow(
    participant: SplitParticipant,
    canRemove: Boolean,
    onRemove: () -> Unit,
    onUpdateName: (String) -> Unit,
    onUpdateShare: (String) -> Unit,
    onToggleSettled: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Avatar
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(
                    if (participant.isSettled) BudgetGood.copy(alpha = 0.2f)
                    else MaterialTheme.colorScheme.primaryContainer
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = participant.name.take(1).uppercase().ifEmpty { "?" },
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = if (participant.isSettled) BudgetGood
                        else MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        // Name field
        OutlinedTextField(
            value = participant.name,
            onValueChange = onUpdateName,
            placeholder = { Text("Name") },
            modifier = Modifier.weight(1f),
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyMedium
        )

        // Amount field
        OutlinedTextField(
            value = participant.shareAmount,
            onValueChange = { onUpdateShare(it.filter { c -> c.isDigit() || c == '.' }) },
            prefix = { Text(com.expensemanager.app.util.AppCurrency.symbol) },
            modifier = Modifier.width(90.dp),
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyMedium
        )

        // Settled toggle
        IconButton(
            onClick = onToggleSettled,
            modifier = Modifier.size(32.dp)
        ) {
            Icon(
                imageVector = if (participant.isSettled) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                contentDescription = if (participant.isSettled) "Settled" else "Mark settled",
                tint = if (participant.isSettled) BudgetGood else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(20.dp)
            )
        }

        // Remove button
        if (canRemove) {
            IconButton(onClick = onRemove, modifier = Modifier.size(28.dp)) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Remove",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}
