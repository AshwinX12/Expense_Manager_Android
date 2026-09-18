package com.expensemanager.app.ui.lending

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.expensemanager.app.data.db.entity.*
import com.expensemanager.app.data.repository.LendingRepository
import com.expensemanager.app.ui.components.*
import com.expensemanager.app.ui.navigation.LocalNavReselectEvent
import com.expensemanager.app.ui.navigation.Screen
import com.expensemanager.app.ui.theme.*
import com.expensemanager.app.util.formatCurrency
import com.expensemanager.app.util.formatDisplay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class LendingViewModel @Inject constructor(
    private val lendingRepository: LendingRepository
) : ViewModel() {
    val entries = lendingRepository.getAllFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val totalLent = lendingRepository.getTotalLentFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), BigDecimal.ZERO)
    val totalBorrowed = lendingRepository.getTotalBorrowedFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), BigDecimal.ZERO)

    fun addEntry(name: String, amount: BigDecimal, type: LendingType, note: String) {
        viewModelScope.launch {
            lendingRepository.insert(LendingEntity(
                personName = name, amount = amount, type = type,
                date = LocalDate.now(), note = note.ifBlank { null }
            ))
        }
    }

    fun markSettled(id: Long) {
        viewModelScope.launch { lendingRepository.markSettled(id) }
    }

    fun delete(entry: LendingEntity) {
        viewModelScope.launch { lendingRepository.delete(entry) }
    }

    fun recordPayment(id: Long, amount: BigDecimal) {
        viewModelScope.launch { lendingRepository.recordPayment(id, amount) }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun LendingScreen(
    onNavigateBack: () -> Unit,
    viewModel: LendingViewModel = hiltViewModel()
) {
    val entries by viewModel.entries.collectAsStateWithLifecycle()
    val totalLent by viewModel.totalLent.collectAsStateWithLifecycle()
    val totalBorrowed by viewModel.totalBorrowed.collectAsStateWithLifecycle()
    var showAddDialog by remember { mutableStateOf(false) }
    var showRepaymentDialogFor by remember { mutableStateOf<LendingEntity?>(null) }
    var selectedEntryForMenu by remember { mutableStateOf<LendingEntity?>(null) }
    var filterType by remember { mutableStateOf<LendingType?>(null) }
    
    val filteredEntries = remember(entries, filterType) {
        if (filterType == null) entries else entries.filter { it.type == filterType }
    }
    
    val listState = rememberLazyListState()
    val reselectEvent = LocalNavReselectEvent.current

    LaunchedEffect(reselectEvent) {
        reselectEvent.collect { route ->
            if (route == Screen.Lending.route) {
                listState.animateScrollToItem(0)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lending & Borrowing", fontWeight = FontWeight.SemiBold) },
                actions = { IconButton(onClick = { showAddDialog = true }) { Icon(Icons.Default.Add, "Add") } },
                windowInsets = WindowInsets(0.dp),
                colors = flatTopAppBarColors()
            )
        }
    ) { padding ->
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(Dimens.ScreenPadding),
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacingSm)
        ) {
            // Summary
            item {
                Row(Modifier.fillMaxWidth(), Arrangement.spacedBy(Dimens.SpacingSm)) {
                    Card(Modifier.weight(1f), shape = CardShape) {
                        Column(Modifier.padding(Dimens.CardPaddingSmall)) {
                            Text("You're Owed", style = MaterialTheme.typography.labelSmall, color = IncomeGreen)
                            Text(totalLent.formatCurrency(), style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold, color = IncomeGreen)
                        }
                    }
                    Card(Modifier.weight(1f), shape = CardShape) {
                        Column(Modifier.padding(Dimens.CardPaddingSmall)) {
                            Text("You Owe", style = MaterialTheme.typography.labelSmall, color = ExpenseRed)
                            Text(totalBorrowed.formatCurrency(), style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold, color = ExpenseRed)
                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Dimens.SpacingSm)
                ) {
                    FilterChip(
                        selected = filterType == null,
                        onClick = { filterType = null },
                        label = { Text("All") }
                    )
                    LendingType.entries.forEach { type ->
                        FilterChip(
                            selected = filterType == type,
                            onClick = { filterType = type },
                            label = { Text(type.name.lowercase().replaceFirstChar { it.uppercase() }) }
                        )
                    }
                }
            }

            items(filteredEntries, key = { it.id }) { entry ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedEntryForMenu = entry },
                    shape = CardShape, 
                    elevation = CardDefaults.cardElevation(Dimens.CardElevation)
                ) {
                    ListItem(
                        headlineContent = { Text(entry.personName, fontWeight = FontWeight.Medium) },
                        supportingContent = {
                            Column {
                                Text(
                                    "${if (entry.type == LendingType.LENT) "Lent" else "Borrowed"} · ${entry.date.formatDisplay()}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Text(
                                    entry.status.name.lowercase().replace('_', ' ').replaceFirstChar { it.uppercase() },
                                    style = MaterialTheme.typography.labelSmall,
                                    color = when (entry.status) {
                                        LendingStatus.SETTLED -> BudgetGood
                                        LendingStatus.PARTIALLY_REPAID -> BudgetWarning
                                        LendingStatus.PENDING -> ExpenseRed
                                    }
                                )
                            }
                        },
                        trailingContent = {
                            Column(horizontalAlignment = Alignment.End) {
                                AmountText(
                                    amount = entry.amount - entry.repaidAmount,
                                    type = if (entry.type == LendingType.LENT) TransactionType.DEBT_TRANSFER_IN else TransactionType.DEBT_TRANSFER_OUT,
                                    style = AmountTextStyle.Compact
                                )
                                if (entry.status != LendingStatus.SETTLED) {
                                    TextButton(onClick = { viewModel.markSettled(entry.id) }) {
                                        Text("Settle", style = MaterialTheme.typography.labelSmall)
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }

    if (selectedEntryForMenu != null) {
        ModalBottomSheet(onDismissRequest = { selectedEntryForMenu = null }) {
            Column(Modifier.fillMaxWidth().padding(Dimens.ScreenPadding)) {
                Text(
                    text = "Manage ${selectedEntryForMenu?.personName}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = Dimens.SpacingMd)
                )
                ListItem(
                    headlineContent = { Text("Log Partial Repayment") },
                    leadingContent = { Icon(Icons.Default.Payment, contentDescription = "Repay", tint = MaterialTheme.colorScheme.primary) },
                    modifier = Modifier.clickable {
                        showRepaymentDialogFor = selectedEntryForMenu
                        selectedEntryForMenu = null
                    }
                )
                ListItem(
                    headlineContent = { Text("Delete") },
                    leadingContent = { Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error) },
                    modifier = Modifier.clickable {
                        selectedEntryForMenu?.let { viewModel.delete(it) }
                        selectedEntryForMenu = null
                    }
                )
                Spacer(modifier = Modifier.height(Dimens.SpacingXl))
            }
        }
    }

    if (showAddDialog) {
        var name by remember { mutableStateOf("") }
        var amount by remember { mutableStateOf("") }
        var type by remember { mutableStateOf(LendingType.LENT) }
        var note by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Add Lending Entry") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(Dimens.SpacingSm)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(Dimens.SpacingSm)) {
                        LendingType.entries.forEach { t ->
                            FilterChip(selected = type == t, onClick = { type = t },
                                label = { Text(t.name.lowercase().replaceFirstChar { it.uppercase() }) })
                        }
                    }
                    OutlinedTextField(name, { name = it }, label = { Text("Person Name") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(amount, { amount = it }, label = { Text("Amount") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(note, { note = it }, label = { Text("Note (optional)") }, modifier = Modifier.fillMaxWidth())
                }
            },
            confirmButton = {
                Button(onClick = {
                    amount.toBigDecimalOrNull()?.let {
                        viewModel.addEntry(name, it, type, note); showAddDialog = false
                    }
                }) { Text("Save") }
            },
            dismissButton = { TextButton(onClick = { showAddDialog = false }) { Text("Cancel") } }
        )
    }

    if (showRepaymentDialogFor != null) {
        var repayAmount by remember { mutableStateOf("") }
        val maxAmount = showRepaymentDialogFor!!.amount - showRepaymentDialogFor!!.repaidAmount
        AlertDialog(
            onDismissRequest = { showRepaymentDialogFor = null },
            title = { Text("Log Partial Repayment") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(Dimens.SpacingSm)) {
                    Text("Remaining amount: ${maxAmount.formatCurrency()}")
                    OutlinedTextField(
                        value = repayAmount,
                        onValueChange = { repayAmount = it },
                        label = { Text("Amount") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    repayAmount.toBigDecimalOrNull()?.let {
                        val finalAmount = if (it > maxAmount) maxAmount else it
                        viewModel.recordPayment(showRepaymentDialogFor!!.id, finalAmount)
                        showRepaymentDialogFor = null
                    }
                }) { Text("Save") }
            },
            dismissButton = { TextButton(onClick = { showRepaymentDialogFor = null }) { Text("Cancel") } }
        )
    }
}
