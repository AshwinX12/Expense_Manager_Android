package com.expensemanager.app.ui.transactions

import androidx.compose.animation.*
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.expensemanager.app.ui.components.*
import com.expensemanager.app.ui.theme.*
import com.expensemanager.app.ui.planning.AddScheduledDialog
import com.expensemanager.app.ui.planning.ScheduledViewModel
import com.expensemanager.app.ui.navigation.LocalNavReselectEvent
import com.expensemanager.app.ui.navigation.Screen
import com.expensemanager.app.util.formatCurrency
import java.math.BigDecimal
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsScreen(
    onTransactionClick: (Long) -> Unit,
    onAddClick: () -> Unit,
    viewModel: TransactionsViewModel = hiltViewModel(),
    scheduledViewModel: ScheduledViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val scheduledState by scheduledViewModel.uiState.collectAsStateWithLifecycle()
    val filteredTransactions = remember(state) { viewModel.getFilteredTransactions() }
    var showFilterSheet by remember { mutableStateOf(false) }
    var showSortMenu by remember { mutableStateOf(false) }
    var selectedTransactionForMenu by remember { mutableStateOf<com.expensemanager.app.domain.model.TransactionWithDetails?>(null) }
    var showAddScheduledDialogFor by remember { mutableStateOf<com.expensemanager.app.domain.model.TransactionWithDetails?>(null) }
    val listState = rememberLazyListState()
    val reselectEvent = LocalNavReselectEvent.current

    LaunchedEffect(reselectEvent) {
        reselectEvent.collect { route ->
            if (route == Screen.Transactions.route) {
                listState.animateScrollToItem(0)
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.ScreenPadding, vertical = Dimens.SpacingMd),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Transactions",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Row {
                IconButton(onClick = { showFilterSheet = true }) {
                    Badge(
                        modifier = Modifier.size(if (state.filter.isActive) 8.dp else 0.dp),
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {}
                    Icon(Icons.Default.FilterList, "Filter")
                }
                Box {
                    IconButton(onClick = { showSortMenu = true }) {
                        Icon(Icons.Default.Sort, "Sort")
                    }
                    DropdownMenu(
                        expanded = showSortMenu,
                        onDismissRequest = { showSortMenu = false }
                    ) {
                        SortBy.entries.forEach { sort ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        when (sort) {
                                            SortBy.DATE_DESC -> "Newest first"
                                            SortBy.DATE_ASC -> "Oldest first"
                                            SortBy.AMOUNT_DESC -> "Highest amount"
                                            SortBy.AMOUNT_ASC -> "Lowest amount"
                                            SortBy.CATEGORY -> "Category"
                                        }
                                    )
                                },
                                onClick = {
                                    viewModel.setSortBy(sort)
                                    showSortMenu = false
                                },
                                leadingIcon = {
                                    if (state.sortBy == sort) {
                                        Icon(Icons.Default.Check, null, tint = MaterialTheme.colorScheme.primary)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }

        // Search bar
        SearchBarComponent(
            query = state.searchQuery,
            onQueryChange = { viewModel.setSearchQuery(it) },
            placeholder = "Search transactions…",
            modifier = Modifier.padding(horizontal = Dimens.ScreenPadding)
        )

        Spacer(modifier = Modifier.height(Dimens.SpacingMd))

        // Results count and expenditure
        val totalFilteredExpense = filteredTransactions
            .filter { it.transaction.type == com.expensemanager.app.data.db.entity.TransactionType.EXPENSE }
            .sumOf { it.transaction.amount }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.ScreenPadding),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${filteredTransactions.size} transactions",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            if (totalFilteredExpense > BigDecimal.ZERO) {
                Text(
                    text = "Total Expense: ${totalFilteredExpense.formatCurrency()}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(Dimens.SpacingSm))

        // Transaction list
        if (filteredTransactions.isEmpty() && !state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                EmptyStateView(
                    icon = {
                        Icon(
                            Icons.Default.SearchOff,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    title = if (state.searchQuery.isNotBlank() || state.filter.isActive)
                        "No matching transactions" else "No transactions yet",
                    message = if (state.searchQuery.isNotBlank() || state.filter.isActive)
                        "Try adjusting your search or filters" else "Tap + to add your first transaction"
                )
            }
        } else {
            val groupedTransactions = remember(filteredTransactions) {
                filteredTransactions.groupBy { it.transaction.date }.toSortedMap(compareByDescending { it })
            }

            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(
                    bottom = Dimens.SpacingHuge + Dimens.BottomBarHeight
                ),
                verticalArrangement = Arrangement.spacedBy(Dimens.SpacingSm)
            ) {
                groupedTransactions.forEach { (date, dailyTransactions) ->
                    item(key = "group_${date.toEpochDay()}", contentType = "transaction_group") {
                        com.expensemanager.app.ui.components.TransactionGroupCard(
                            date = date,
                            transactions = dailyTransactions,
                            onTransactionClick = { onTransactionClick(it) },
                            onTransactionLongClick = { selectedTransactionForMenu = it },
                            modifier = Modifier.padding(horizontal = Dimens.ScreenPadding)
                        )
                    }
                }
            }
        }
    }

    // Filter bottom sheet
    if (showFilterSheet) {
        FilterBottomSheet(
            currentFilter = state.filter,
            categories = state.categories,
            accounts = state.accounts,
            onApply = { filter ->
                viewModel.setFilter(filter)
                showFilterSheet = false
            },
            onDismiss = { showFilterSheet = false }
        )
    }

    selectedTransactionForMenu?.let { txDetail ->
        TransactionContextMenuSheet(
            transaction = txDetail,
            onDismiss = { selectedTransactionForMenu = null },
            onDuplicate = {
                viewModel.duplicateTransaction(txDetail)
                selectedTransactionForMenu = null
            },
            onDelete = {
                viewModel.deleteTransaction(txDetail.transaction.id)
            },
            onSchedule = {
                showAddScheduledDialogFor = txDetail
                selectedTransactionForMenu = null
            }
        )
    }

    showAddScheduledDialogFor?.let { txDetail ->
        AddScheduledDialog(
            initialAmount = txDetail.transaction.amount.toPlainString(),
            initialAccountId = txDetail.transaction.accountId,
            categories = scheduledState.categories,
            accounts = scheduledState.accounts,
            onDismiss = { showAddScheduledDialogFor = null },
            onSave = { amount, type, freq, nextDate, time, categoryId, accountId ->
                scheduledViewModel.addRule(amount, type, freq, nextDate, time, categoryId, accountId)
                showAddScheduledDialogFor = null
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FilterBottomSheet(
    currentFilter: com.expensemanager.app.domain.model.FilterCriteria,
    categories: List<com.expensemanager.app.data.db.entity.CategoryEntity>,
    accounts: List<com.expensemanager.app.data.db.entity.AccountEntity>,
    onApply: (com.expensemanager.app.domain.model.FilterCriteria) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedCategoryIds by remember { mutableStateOf(currentFilter.categoryIds) }
    var selectedAccountIds by remember { mutableStateOf(currentFilter.accountIds) }
    var selectedTypes by remember { mutableStateOf(currentFilter.transactionTypes) }
    
    var customStartDate by remember { mutableStateOf(currentFilter.startDate) }
    var customEndDate by remember { mutableStateOf(currentFilter.endDate) }
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }
    
    val dateFormatter = remember { java.time.format.DateTimeFormatter.ofPattern("MMM dd, yyyy") }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        shape = BottomSheetShape
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.ScreenPadding)
        ) {
            Text(
                text = "Filter Transactions",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(Dimens.SpacingLg))

            // Transaction type
            Text("Type", style = MaterialTheme.typography.labelLarge)
            Spacer(modifier = Modifier.height(Dimens.SpacingSm))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(Dimens.SpacingSm),
                verticalArrangement = Arrangement.spacedBy(Dimens.SpacingXs)
            ) {
                com.expensemanager.app.data.db.entity.TransactionType.entries.forEach { type ->
                    FilterChip(
                        selected = type in selectedTypes,
                        onClick = {
                            selectedTypes = if (type in selectedTypes) selectedTypes - type
                            else selectedTypes + type
                        },
                        label = { Text(type.name.lowercase().replaceFirstChar { it.uppercase() }) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(Dimens.SpacingLg))

            // Date Range
            Text("Date Range", style = MaterialTheme.typography.labelLarge)
            Spacer(modifier = Modifier.height(Dimens.SpacingSm))
            
            var dateRangeOption by remember { mutableStateOf(
                if (currentFilter.startDate == null) "All Time"
                else if (currentFilter.startDate == LocalDate.now().withDayOfMonth(1)) "This Month"
                else if (currentFilter.startDate == LocalDate.now().minusMonths(1).withDayOfMonth(1)) "Last Month"
                else "Custom"
            ) }
            
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(Dimens.SpacingSm),
                verticalArrangement = Arrangement.spacedBy(Dimens.SpacingXs)
            ) {
                listOf("All Time", "This Month", "Last Month", "Custom").forEach { option ->
                    FilterChip(
                        selected = dateRangeOption == option,
                        onClick = { dateRangeOption = option },
                        label = { Text(option) }
                    )
                }
            }
            
            if (dateRangeOption == "Custom") {
                Spacer(modifier = Modifier.height(Dimens.SpacingSm))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Dimens.SpacingSm)
                ) {
                    OutlinedButton(
                        onClick = { showStartDatePicker = true },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(customStartDate?.format(dateFormatter) ?: "Start Date")
                    }
                    OutlinedButton(
                        onClick = { showEndDatePicker = true },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(customEndDate?.format(dateFormatter) ?: "End Date")
                    }
                }
            }

            Spacer(modifier = Modifier.height(Dimens.SpacingLg))

            // Categories
            Text("Categories", style = MaterialTheme.typography.labelLarge)
            Spacer(modifier = Modifier.height(Dimens.SpacingSm))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(Dimens.SpacingSm),
                verticalArrangement = Arrangement.spacedBy(Dimens.SpacingXs)
            ) {
                categories.forEach { category ->
                    FilterChip(
                        selected = category.id in selectedCategoryIds,
                        onClick = {
                            selectedCategoryIds = if (category.id in selectedCategoryIds)
                                selectedCategoryIds - category.id
                            else selectedCategoryIds + category.id
                        },
                        label = { Text(category.name) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(Dimens.SpacingXl))

            // Action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimens.SpacingSm)
            ) {
                OutlinedButton(
                    onClick = {
                        onApply(com.expensemanager.app.domain.model.FilterCriteria())
                    },
                    modifier = Modifier.weight(1f)
                ) { Text("Clear") }

                Button(
                    onClick = {
                        val now = LocalDate.now()
                        val (start, end) = when (dateRangeOption) {
                            "This Month" -> now.withDayOfMonth(1) to now.withDayOfMonth(now.lengthOfMonth())
                            "Last Month" -> {
                                val lastM = now.minusMonths(1)
                                lastM.withDayOfMonth(1) to lastM.withDayOfMonth(lastM.lengthOfMonth())
                            }
                            "Custom" -> customStartDate to customEndDate
                            else -> null to null
                        }
                        
                        onApply(
                            currentFilter.copy(
                                categoryIds = selectedCategoryIds,
                                accountIds = selectedAccountIds,
                                transactionTypes = selectedTypes,
                                startDate = start,
                                endDate = end
                            )
                        )
                    },
                    modifier = Modifier.weight(1f)
                ) { Text("Apply") }
            }

            Spacer(modifier = Modifier.height(Dimens.SpacingXl))
        }
    }
    
    if (showStartDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = customStartDate?.toEpochDay()?.times(86400000)
        )
        DatePickerDialog(
            onDismissRequest = { showStartDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        customStartDate = java.time.Instant.ofEpochMilli(millis)
                            .atZone(java.time.ZoneId.systemDefault())
                            .toLocalDate()
                    }
                    showStartDatePicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showStartDatePicker = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
    
    if (showEndDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = customEndDate?.toEpochDay()?.times(86400000)
        )
        DatePickerDialog(
            onDismissRequest = { showEndDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        customEndDate = java.time.Instant.ofEpochMilli(millis)
                            .atZone(java.time.ZoneId.systemDefault())
                            .toLocalDate()
                    }
                    showEndDatePicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showEndDatePicker = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}
